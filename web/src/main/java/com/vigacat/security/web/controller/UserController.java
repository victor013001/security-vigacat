package com.vigacat.security.web.controller;

import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.service.component.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto getUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "app-id") Long appId) {
        return userService.getUser(username, appId);
    }

}
