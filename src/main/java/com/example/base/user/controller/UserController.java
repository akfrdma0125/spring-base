package com.example.base.user.controller;

import com.example.base.user.controller.port.AuthenticationService;
import com.example.base.user.controller.port.UserCreateService;
import com.example.base.user.controller.port.UserReadService;
import com.example.base.user.controller.port.UserUpdateService;
import com.example.base.user.controller.response.MyProfileResponse;
import com.example.base.user.controller.response.UserResponse;
import com.example.base.user.domain.User;
import com.example.base.user.domain.UserCreate;
import com.example.base.user.domain.UserUpdate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Builder
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserReadService userReadService;
    private final AuthenticationService authenticationService;
    private final UserUpdateService userUpdateService;
    private final UserCreateService userCreateService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserCreate userCreate) {
        User user = userCreateService.create(userCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(UserResponse.from(userReadService.getById(id)));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
        @PathVariable long id,
        @RequestParam String certificationCode) {
        authenticationService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("http://localhost:3000"))
            .build();
    }


    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> getMyInfo(
        @RequestHeader("EMAIL") String email
    ) {
        User user = userReadService.getByEmail(email);
        user = authenticationService.login(user.id());
        return ResponseEntity
            .ok()
            .body(MyProfileResponse.from(user));
    }

    @PutMapping("/me")
    public ResponseEntity<MyProfileResponse> updateMyInfo(
        @RequestHeader("EMAIL") String email,
        @RequestBody UserUpdate userUpdate
    ) {
        User user = userReadService.getByEmail(email);
        user = userUpdateService.update(user.id(), userUpdate);
        return ResponseEntity
            .ok()
            .body(MyProfileResponse.from(user));
    }

}