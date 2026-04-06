package ru.calorai.recognition.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.calorai.food.catalog.model.FoodCatalogEntry;
import ru.calorai.food.dto.FoodCatalogEntryDTO;
import ru.calorai.food.mapper.FoodCatalogDtoMapper;
import ru.calorai.food.recognition.port.in.RecognizeFoodFromImageApi;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/food/recognize")
@Tag(name = "Food Recognition API", description = "Распознавание еды и получение нутриционной информации по фотографии")
public class FoodRecognitionController {

    private final RecognizeFoodFromImageApi recognizeFoodFromImageApi;
    private final FoodCatalogDtoMapper foodCatalogDtoMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Распознать еду по фотографии",
            description = "Принимает фотографию блюда и возвращает нутриционную информацию на основе AI-распознавания"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Еда успешно распознана",
                    content = @Content(schema = @Schema(implementation = FoodCatalogEntryDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "400", description = "Некорректное изображение или файл не передан")
    })
    public ResponseEntity<FoodCatalogEntryDTO> recognize(
            @Parameter(description = "Фотография блюда для распознавания", required = true)
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        FoodCatalogEntry entry = recognizeFoodFromImageApi.recognizeFoodByImage(
                image.getBytes(),
                image.getOriginalFilename());
        return ResponseEntity.ok(foodCatalogDtoMapper.toDto(entry));
    }
}
