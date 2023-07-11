package com.vigacat.security.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToSaveDto {

    private String name;
    private String email;
    private String password;
    private List<RoleDto> roles;
}
