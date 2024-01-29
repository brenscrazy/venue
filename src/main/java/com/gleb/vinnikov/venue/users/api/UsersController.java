package com.gleb.vinnikov.venue.users.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.users.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Методы для работы с пользователями")
public class UsersController {

    private final UsersService usersService;

    @Operation(
            summary = "Получить информацию о пользователе по его username.",
            description = "Возвращает общедоступную информацию о пользователе с переданным username."
    )
    @GetMapping(
            value = "get-user-by-username"
    )
    public ResponseEntity<UserResponsePublic> getUserByUsername(
            @AuthenticationPrincipal User principal,
            @RequestParam String username
    ) {
        return ResponseEntity.ok(usersService.getUserByUsername(username));
    }

    @Operation(
            summary = "Изменить информацию авторизованного пользователя.",
            description = "Обновляет переданные поля пользователя. В случае изменения username текущий JWT токен " +
                    "становится недействителен."
    )
    @PostMapping(
            value = "change-my-info"
    )
    public ResponseEntity<UserResponsePrivate> updateUserInfo(
            @AuthenticationPrincipal User principal,
            @Valid @RequestBody ChangeUserInfoRequest request
    ) {
        return ResponseEntity.ok(usersService.updateUser(principal, request));
    }

}
