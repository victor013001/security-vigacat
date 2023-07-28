package com.vigacat.security.service.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class UserCreateException extends RuntimeException {

    private final String username;
    private final String userEmail;
    private final HttpStatus httpStatus;
    private final Type type;
    private final List<Long> roleIds;

    public UserCreateException(String username, String userEmail, Type type) {
        super(type.getMessage());
        this.username = username;
        this.userEmail = userEmail;
        this.type = type;
        this.httpStatus = type.getHttpStatus();
        this.roleIds = null;
    }

    public UserCreateException(List<Long> roleIds, Type type) {
        super(type.getMessage());
        this.roleIds = roleIds;
        this.type = type;
        this.httpStatus = type.getHttpStatus();
        this.username = null;
        this.userEmail = null;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        DUPLICATE_NAME_EMAIL("Name or email already exist", HttpStatus.CONFLICT),
        NON_EXISTING_ROLES("One or more role does not exist", HttpStatus.BAD_REQUEST);

        private final String message;
        private final HttpStatus httpStatus;
    }
}
