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

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", roleCreateException.getType());
        body.put("title", roleCreateException.getHttpStatus().name());
        body.put("status", roleCreateException.getHttpStatus().value());
        body.put("role-name", roleCreateException.getRoleName());
        body.put("app-id", roleCreateException.getAppId());
        body.put("detail", roleCreateException.getMessage());

        return ResponseEntity.status(roleCreateException.getHttpStatus())
                .body(body);
    }

    @ExceptionHandler(UserCreateException.class)
    protected ResponseEntity<Map<String, Object>> handleUserCreateException(UserCreateException userCreateException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", userCreateException.getType());
        body.put("title", userCreateException.getHttpStatus().name());
        body.put("status", userCreateException.getHttpStatus().value());
        body.put("user-name", userCreateException.getUsername());
        body.put("user-email", userCreateException.getUserEmail());
        body.put("detail", userCreateException.getMessage());

        return ResponseEntity.status(userCreateException.getHttpStatus())
                .body(body);
    }

}
