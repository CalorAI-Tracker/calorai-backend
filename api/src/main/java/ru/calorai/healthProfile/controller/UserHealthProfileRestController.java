package ru.calorai.healthProfile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.calorai.healthProfile.dto.UserHealthProfileDTO;
import ru.calorai.healthProfile.dto.request.CreateUserHealthProfileRequest;
import ru.calorai.healthProfile.dto.request.UpdateUserHealthProfileRequest;
import ru.calorai.healthProfile.mapper.UserHealthProfileDtoMapper;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.heathProfile.port.in.CreateUserHealthProfileApi;
import ru.calorai.heathProfile.port.in.FindUserHealthProfileApi;
import ru.calorai.heathProfile.port.in.UpdateUserHealthProfileApi;

@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "User Profile", description = "API для управления пользовательским профилем")
public class UserHealthProfileRestController {

    private final CreateUserHealthProfileApi createUserHealthProfileApi;
    private final FindUserHealthProfileApi findUserHealthProfileApi;
    private final UpdateUserHealthProfileApi updateUserHealthProfileApi;

    private final UserHealthProfileDtoMapper userHealthProfileDtoMapper;

    @Operation(
            summary = "Создать профиль пользователя",
            description = "Создает новый профиль пользователя и возвращает его идентификатор."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Профиль успешно создан",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
    })
    @PostMapping
    public ResponseEntity<Long> createUserProfile(
            @Parameter(description = "Данные для создания профиля пользователя", required = true)
            @Valid @RequestBody CreateUserHealthProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                createUserHealthProfileApi.createUserHealthProfile(
                        userHealthProfileDtoMapper.toDomain(request))
        );
    }

    @Operation(
            summary = "Получить профиль пользователя по ID пользователя",
            description = "Возвращает профиль пользователя по его ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль найден",
                    content = @Content(schema = @Schema(implementation = UserHealthProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Профиль не найден", content = @Content),
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserHealthProfileDTO> getUserProfileByUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok().body(
                userHealthProfileDtoMapper.toDto(findUserHealthProfileApi.findUserProfileByUserId(userId))
        );
    }

    @Operation(
            summary = "Обновить профиль пользователя",
            description = "Обновляет данные профиля пользователя по его ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль успешно обновлён",
                    content = @Content(schema = @Schema(implementation = UserHealthProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Профиль не найден", content = @Content),
    })
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserHealthProfileDTO> updateUserProfile(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(description = "Новые данные профиля", required = true)
            @Valid @RequestBody UpdateUserHealthProfileRequest request
    ) {
        UserHealthProfile updatedProfile = updateUserHealthProfileApi.updateUserHealthProfile(
                userId,
                userHealthProfileDtoMapper.toDomain(request)
        );
        UserHealthProfileDTO dto = userHealthProfileDtoMapper.toDto(updatedProfile);
        return ResponseEntity.ok(dto);
    }
}
