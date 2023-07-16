package com.vigacat.security.service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class RoleCreateException extends RuntimeException {

    private static final Map<Type, HttpStatus> TYPE_HTTP_STATUS_MAP = Map.of(
            Type.DUPLICATE_NAME, HttpStatus.CONFLICT,
            Type.NON_EXISTENT_PERMISSIONS, HttpStatus.BAD_REQUEST
    );

    private final String roleName;
    private final Long appId;
    private final Type type;
    private final HttpStatus httpStatus;

    public RoleCreateException(String message, String roleName, Long appId, Type type) {
        super(message);
        this.roleName = roleName;
        this.appId = appId;
        this.type = type;
        this.httpStatus = TYPE_HTTP_STATUS_MAP.get(type);
    }

    public enum Type {
        DUPLICATE_NAME,
        NON_EXISTENT_PERMISSIONS
    }

}
