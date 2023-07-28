package com.vigacat.security.web.exceptions;

import com.vigacat.security.service.exceptions.RoleCreateException;
import com.vigacat.security.service.exceptions.UserCreateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoleCreateException.class)
    protected ResponseEntity<Map<String, Object>> handleRoleCreateException(RoleCreateException roleCreateException) {
        return ResponseEntity.status(roleCreateException.getHttpStatus())
                .body(roleExceptionBody(roleCreateException));
    }

    @ExceptionHandler(UserCreateException.class)
    protected ResponseEntity<Map<String, Object>> handleUserCreateException(UserCreateException userCreateException) {
        return ResponseEntity.status(userCreateException.getHttpStatus())
                .body(userCreateExceptionBody(userCreateException));
    }

    private Map<String, Object> roleExceptionBody(RoleCreateException roleCreateException) {
        RoleCreateException.Type exceptionType = roleCreateException.getType();
        if (exceptionType.equals(RoleCreateException.Type.DUPLICATE_NAME)) {
            return createDuplicateNameBody(roleCreateException);
        } else {
            return createNonExistentPermissionsBody(roleCreateException);
        }
    }

    private Map<String, Object> createDuplicateNameBody(RoleCreateException roleCreateException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", roleCreateException.getType());
        body.put("title", roleCreateException.getHttpStatus().name());
        body.put("status", roleCreateException.getHttpStatus().value());
        body.put("role-name", roleCreateException.getRoleName());
        body.put("app-id", roleCreateException.getAppId());
        body.put("detail", roleCreateException.getMessage());
        return body;
    }

    private Map<String, Object> createNonExistentPermissionsBody(RoleCreateException roleCreateException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", roleCreateException.getType());
        body.put("title", roleCreateException.getHttpStatus().name());
        body.put("status", roleCreateException.getHttpStatus().value());
        body.put("permission-names", roleCreateException.getRolePermissionNames().toString());
        body.put("app-id", roleCreateException.getAppId());
        body.put("detail", roleCreateException.getMessage());
        return body;
    }

    private Map<String, Object> userCreateExceptionBody(UserCreateException userCreateException) {
        UserCreateException.Type exceptionType = userCreateException.getType();
        if (exceptionType.equals(UserCreateException.Type.DUPLICATE_NAME_EMAIL)) {
            return createDuplicateNameEmailBody(userCreateException);
        } else {
            return createNonExistentRolesBody(userCreateException);
        }

    }

    private Map<String, Object> createDuplicateNameEmailBody(UserCreateException userCreateException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", userCreateException.getType());
        body.put("title", userCreateException.getHttpStatus().name());
        body.put("status", userCreateException.getHttpStatus().value());
        body.put("user-name", userCreateException.getUsername());
        body.put("user-email", userCreateException.getUserEmail());
        body.put("detail", userCreateException.getMessage());
        return body;
    }

    private Map<String, Object> createNonExistentRolesBody(UserCreateException userCreateException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", userCreateException.getType());
        body.put("title", userCreateException.getHttpStatus().name());
        body.put("status", userCreateException.getHttpStatus().value());
        body.put("role-ids", userCreateException.getRoleIds().toString());
        body.put("detail", userCreateException.getMessage());
        return body;
    }

}
