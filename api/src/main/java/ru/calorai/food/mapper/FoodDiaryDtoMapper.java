package ru.calorai.food.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.dailyNutririon.model.DailyMealComposition;
import ru.calorai.food.dto.DailyMealCompositionDTO;
import ru.calorai.food.dto.request.CreateFoodDiaryEntryRequest;
import ru.calorai.food.EMeal;
import ru.calorai.food.diary.model.cmd.CreateFoodDiaryEntryCommand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FoodDiaryDtoMapper {

    CreateFoodDiaryEntryCommand toCommand(CreateFoodDiaryEntryRequest request);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "meals", source = "meals", qualifiedByName = "domainMealsToDto")
    DailyMealCompositionDTO toDto(DailyMealComposition domain);

    @org.mapstruct.Named("domainMealsToDto")
    default Map<String, List<DailyMealCompositionDTO.MealProductDTO>> domainMealsToDto(
            Map<EMeal, List<DailyMealComposition.MealItem>> domainMeals) {
        return domainMeals.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().stream()
                                .map(this::mealItemToProductDto)
                                .collect(Collectors.toList())
                ));
    }

    default DailyMealCompositionDTO.MealProductDTO mealItemToProductDto(DailyMealComposition.MealItem item) {
        return DailyMealCompositionDTO.MealProductDTO.builder()
                .id(item.getId())
                .entryName(item.getEntryName())
                .quantityGrams(item.getQuantityGrams())
                .kcal(item.getMacros().getKcal())
                .proteinG(item.getMacros().getProteinG() != null ?
                        item.getMacros().getProteinG().doubleValue() : 0.0)
                .fatG(item.getMacros().getFatG() != null ?
                        item.getMacros().getFatG().doubleValue() : 0.0)
                .carbsG(item.getMacros().getCarbsG() != null ?
                        item.getMacros().getCarbsG().doubleValue() : 0.0)
                .build();
    }
}
