package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.RoleDto;

import java.util.List;

public interface RolePersistence {

    RoleDto saveNewRole(RoleDto roleDto, Long appId, String usernameAuthenticated);

    boolean roleNameExist(String roleName, Long appId);

    boolean roleIdsExist(List<Long> roleIds);
}
