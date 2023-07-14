package com.vigacat.security.service.exceptions;

import org.apache.commons.lang3.StringUtils;

public class RoleCreateException extends RuntimeException {

    private String roleName;
    private Long appId;
    private Type type;

    public RoleCreateException(String message, String roleName, Long appId, Type type) {
        super(message);
        this.roleName = StringUtils.isEmpty(roleName) ? "" : roleName;
        this.appId = appId;
        this.type = type;
    }

    public enum Type {
        INVALID_PARAMETERS,
        DUPLICATE_NAME,
        NON_EXISTENT_PERMISSIONS
    }
}
