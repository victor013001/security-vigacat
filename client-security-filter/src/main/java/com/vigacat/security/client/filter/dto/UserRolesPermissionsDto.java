package com.vigacat.security.client.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesPermissionsDto {
    private String name;
    private List<RoleDto> roles;
    private List<PermissionDto> permissions;
}
