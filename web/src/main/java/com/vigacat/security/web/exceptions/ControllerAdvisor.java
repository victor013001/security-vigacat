package com.vigacat.security.web.exceptions;

import com.vigacat.security.service.exceptions.RoleCreateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoleCreateException.class)
    protected ResponseEntity<Object> handleRoleCreateException(RoleCreateException roleCreateException) {

        Map<String,Object> body = Map.of(
                "Role-Name", roleCreateException.getRoleName(),
                "App-Id", roleCreateException.getAppId(),
                "Type", roleCreateException.getType(),
                "Message", roleCreateException.getMessage()
        );

        return new ResponseEntity<>(body, roleCreateException.getHttpStatus());
    }

}
