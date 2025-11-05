package ru.calorai.userProfile.controller;

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
import ru.calorai.userProfile.dto.UserProfileDTO;
import ru.calorai.userProfile.dto.request.CreateUserProfileRequest;
import ru.calorai.userProfile.mapper.UserProfileDtoMapper;
import ru.calorai.profile.port.in.CreateUserProfileApi;
import ru.calorai.profile.port.in.FindUserProfileApi;

@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "User Profile", description = "API для управления пользовательским профилем")
public class UserProfileRestController {

    private final CreateUserProfileApi createUserProfileApi;
    private final FindUserProfileApi findUserProfileApi;

    private final UserProfileDtoMapper userProfileDtoMapper;

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
            @Valid @RequestBody CreateUserProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                createUserProfileApi.createUserProfile(
                        userProfileDtoMapper.toDomain(request))
        );
    }

    @Operation(
            summary = "Получить профиль пользователя по ID пользователя",
            description = "Возвращает профиль пользователя по его ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль найден",
                    content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Профиль не найден", content = @Content),
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfileByUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok().body(
                userProfileDtoMapper.toDto(findUserProfileApi.findUserProfileByUserId(userId))
        );
    }
}
