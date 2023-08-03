package com.vigacat.security.web.controller;

import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserRolesPermissionsDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.service.component.UserService;
import com.vigacat.security.web.constant.HeaderConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('permission::SEC_QUERY_USERS')")
    public UserDto getUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "app-id") Long appId) {
        return userService.getUser(username, appId);
    }

    @GetMapping(path = "/no-relations")
    public UserDto getUserSimple(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "app-id") Long appId) {
        return userService.getUserWithoutFetch(username, appId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('permission::SEC_CREATE_USERS')")
    public UserDto createNewUser(@Valid @RequestBody UserToSaveDto userRequest) {
        return userService.createNewUser(userRequest);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('permission::SEC_QUERY_USERS')")
    public UserRolesPermissionsDto getUserRolesAndPermissions(@RequestHeader(HeaderConstant.APPLICATION_NAME) String appName,
                                                              @RequestHeader(HeaderConstant.AUTHORIZATION_NAME) String authorization) {
        return userService.getUserRolesAndPermissionsByTokenAndAppName(authorization, appName);
    }

}
