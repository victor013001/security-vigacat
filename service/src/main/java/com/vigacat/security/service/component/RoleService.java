package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto createNewRole(String roleName, List<String> rolePermissionNames, Long appId);

}
