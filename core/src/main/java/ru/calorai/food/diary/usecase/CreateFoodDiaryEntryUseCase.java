package ru.calorai.food.diary.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.service.RecalcDailyIntakeTotalsService;
import ru.calorai.common.util.NutritionCalculator;
import ru.calorai.food.catalog.exception.FoodCatalogEntryNotFound;
import ru.calorai.food.catalog.model.FoodCatalogEntry;
import ru.calorai.food.diary.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.food.diary.model.FoodDiaryEntry;
import ru.calorai.food.diary.model.cmd.CreateFoodDiaryEntryCommand;
import ru.calorai.food.diary.port.in.CreateFoodDiaryEntryApi;
import ru.calorai.food.catalog.port.out.SaveFoodCatalogEntrySpi;
import ru.calorai.food.diary.port.out.CreateFoodDiaryEntrySpi;

import java.time.LocalDate;

import ru.calorai.food.catalog.port.out.FindFoodCatalogEntryByIdSpi;
import ru.calorai.security.port.in.CurrentUserExtractorApi;

@Service
@RequiredArgsConstructor
public class CreateFoodDiaryEntryUseCase implements CreateFoodDiaryEntryApi {

    private final CreateFoodDiaryEntrySpi createFoodDiaryEntrySpi;
    private final FindFoodCatalogEntryByIdSpi findFoodCatalogEntrySpi;
    private final SaveFoodCatalogEntrySpi saveFoodCatalogEntrySpi;
    private final RecalcDailyIntakeTotalsService recalcDailyIntakeTotalsService;
    private final CurrentUserExtractorApi currentUserExtractor;

    @Override
    @Transactional
    public Long createFoodDiaryEntry(CreateFoodDiaryEntryCommand command) {
        var eatenAt = command.getEatenAt() == null ? LocalDate.now() : command.getEatenAt();

        Long catalogId;

        if (command.getFoodCatalogId() != null) {
            findFoodCatalogEntrySpi.findById(command.getFoodCatalogId())
                    .orElseThrow(() -> new FoodCatalogEntryNotFound(command.getFoodCatalogId()));
            catalogId = command.getFoodCatalogId();
        } else {
            double base = command.getBaseQuantityGrams() != null
                    ? command.getBaseQuantityGrams() : 100.0;

            var proteinPer100g = NutritionCalculator.per100g(command.getProteinPerBaseG(), base);
            var fatPer100g     = NutritionCalculator.per100g(command.getFatPerBaseG(), base);
            var carbsPer100g   = NutritionCalculator.per100g(command.getCarbsPerBaseG(), base);

            var saved = saveFoodCatalogEntrySpi.save(
                    FoodCatalogEntry.builder()
                            .name(command.getEntryName())
                            .brand(command.getBrand())
                            .barcode(command.getBarcode())
                            .provider(command.getProvider() != null ? command.getProvider() : "user")
                            .createdBy(currentUserExtractor.getUser().getId())
                            .proteinPer100g(proteinPer100g)
                            .fatPer100g(fatPer100g)
                            .carbsPer100g(carbsPer100g)
                            .kcalPer100g(NutritionCalculator.calcKcalPer100g(
                                    proteinPer100g, fatPer100g, carbsPer100g))
                            .build()
            );
            catalogId = saved.getId();
        }

        var entry = FoodDiaryEntry.builder()
                .userId(currentUserExtractor.getUser().getId())
                .foodCatalogId(catalogId)
                .eatenAt(eatenAt)
                .meal(command.getMeal())
                .quantityGrams(command.getPortionQuantityGrams())
                .note(command.getNote())
                .build();

        var id = createFoodDiaryEntrySpi.createFoodDiaryEntry(entry);

        // пересчитываем дневную норму
        recalcDailyIntakeTotalsService.recalcForUserAndDate(entry.getUserId(), eatenAt);

        return id;
    }
}
