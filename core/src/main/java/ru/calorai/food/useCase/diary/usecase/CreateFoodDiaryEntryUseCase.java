package ru.calorai.food.useCase.diary.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.service.RecalcDailyIntakeTotalsService;
import ru.calorai.common.util.NutritionCalculator;
import ru.calorai.food.model.FoodCatalogEntry;
import ru.calorai.food.model.FoodDiaryEntry;
import ru.calorai.food.model.cmd.CreateFoodDiaryEntryCommand;
import ru.calorai.food.port.in.diary.CreateFoodDiaryEntryApi;
import ru.calorai.food.port.out.catalog.SaveFoodCatalogEntrySpi;
import ru.calorai.food.port.out.diary.CreateFoodDiaryEntrySpi;

import java.time.LocalDate;

import ru.calorai.food.port.out.catalog.FindFoodCatalogEntryByIdSpi;
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
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Food catalog entry not found: " + command.getFoodCatalogId()));
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
                            .provider("user")
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
