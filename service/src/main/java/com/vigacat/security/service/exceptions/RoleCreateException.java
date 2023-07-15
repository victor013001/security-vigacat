package com.vigacat.security.service.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class RoleCreateException extends RuntimeException {

    private static final Map<Type, HttpStatus> TYPE_HTTP_STATUS_MAP = Map.of(
            Type.INVALID_PARAMETERS, HttpStatus.BAD_REQUEST,
            Type.DUPLICATE_NAME, HttpStatus.CONFLICT,
            Type.NON_EXISTENT_PERMISSIONS, HttpStatus.NOT_FOUND
    );

    private String roleName;
    private Long appId;
    private Type type;
    private HttpStatus httpStatus;

    public RoleCreateException(String message, String roleName, Long appId, Type type) {
        super(message);
        this.roleName = StringUtils.isEmpty(roleName) ? "" : roleName;
        this.appId = appId;
        this.type = type;
        this.httpStatus = TYPE_HTTP_STATUS_MAP.get(type);
    }

    public enum Type {
        INVALID_PARAMETERS,
        DUPLICATE_NAME,
        NON_EXISTENT_PERMISSIONS
    }

}
