package com.vigacat.security.web.dto;

import com.vigacat.security.persistence.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildAuthenticationRequest {
    private TokenDto tokenDto;
    private Long appId;
}
