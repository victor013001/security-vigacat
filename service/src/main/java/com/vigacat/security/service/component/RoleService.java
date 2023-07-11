package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto createNewRole(String roleNameDto, List<String> rolePermissionsDto, String authorization, Long appId);

    List<RoleDto> getRolesByIds(List<Long> roleIds);

}
