package com.vigacat.security.service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class UserCreateException extends RuntimeException {

    private static final Map<Type, HttpStatus> TYPE_HTTP_STATUS_MAP = Map.of(
            Type.DUPLICATE_NAME_EMAIL, HttpStatus.CONFLICT,
            Type.NON_EXISTING_ROLES, HttpStatus.BAD_REQUEST
    );

    private final String username;
    private final String userEmail;
    private final HttpStatus httpStatus;
    private final Type type;

    public UserCreateException(String message, String username, String userEmail, Type type) {
        super(message);
        this.username = username;
        this.userEmail = userEmail;
        this.type = type;
        this.httpStatus = TYPE_HTTP_STATUS_MAP.get(type);
    }

    public enum Type {
        DUPLICATE_NAME_EMAIL,
        NON_EXISTING_ROLES
    }
}
