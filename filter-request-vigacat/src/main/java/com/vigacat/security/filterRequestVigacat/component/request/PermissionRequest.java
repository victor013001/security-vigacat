package com.vigacat.security.filterRequestVigacat.component.request;

import com.vigacat.security.filterRequestVigacat.dto.PermissionDto;

import java.util.List;

public interface PermissionRequest {
    List<PermissionDto> getPermissionsByRoleIds(List<Long> roleIds);
}
