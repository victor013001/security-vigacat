package com.vigacat.security.service.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class RoleCreateException extends RuntimeException {

    private final String roleName;
    private final List<String> rolePermissionNames;
    private final Long appId;
    private final Type type;
    private final HttpStatus httpStatus;

    public RoleCreateException(String roleName, Long appId, Type type) {
        super(type.getMessage());
        this.roleName = roleName;
        this.appId = appId;
        this.type = type;
        this.httpStatus = type.getHttpStatus();
        this.rolePermissionNames = null;

    }

    public RoleCreateException(List<String> rolePermissionNames, Long appId, Type type) {
        super(type.getMessage());
        this.rolePermissionNames = rolePermissionNames;
        this.appId = appId;
        this.type = type;
        this.httpStatus = type.getHttpStatus();
        this.roleName = null;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Type {
        DUPLICATE_NAME("Role name already exists in the app", HttpStatus.CONFLICT),
        NON_EXISTENT_PERMISSIONS("One or more permission doesn't exist", HttpStatus.BAD_REQUEST);

        private final String message;
        private final HttpStatus httpStatus;
    }

}
