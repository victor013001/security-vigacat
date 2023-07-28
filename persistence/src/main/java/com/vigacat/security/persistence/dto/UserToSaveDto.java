package com.vigacat.security.persistence.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "User name is mandatory")
    private String name;

    @Email(message = "Email format is not valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotEmpty(message = "The list of roles is mandatory")
    private List<Long> roleIds;
}
