package com.vigacat.security.web.controller;

import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.service.component.RoleService;
import com.vigacat.security.web.dto.RoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('permission::SEC_CREATE_ROLES')")
    public RoleDto createNewRole(@RequestHeader("app_id") Long appId,
                                 @RequestBody RoleRequest roleRequest) {

        return roleService.createNewRole(roleRequest.getName(), roleRequest.getPermissions(), appId);
    }
}
