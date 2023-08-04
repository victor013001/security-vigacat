package com.vigacat.security.client.filter.component.service;

import com.vigacat.security.client.filter.component.webclient.UserRequestClient;
import com.vigacat.security.client.filter.dto.PermissionDto;
import com.vigacat.security.client.filter.dto.RoleDto;
import com.vigacat.security.client.filter.dto.UserRolesPermissionsDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationClientServiceImplTest {

    @InjectMocks
    private AuthenticationClientServiceImpl authenticationClientService;

    @Mock
    private UserRequestClient clientRequestUser;

    @Test
    public void buildAuthentication() {

        String userToken = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        String permissionRead = "Read";
        String username = "User";
        String roleUser = "User";

        List<RoleDto> userRoleDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .name(roleUser)
                        .build()
        );
        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission(permissionRead)
                .build();
        List<PermissionDto> userPermissionDtos = List.of(
                permissionDtoRead
        );

        UserRolesPermissionsDto userRolesPermissionsDto = UserRolesPermissionsDto.builder()
                .name(username)
                .roles(userRoleDtos)
                .permissions(userPermissionDtos)
                .build();

        Mockito.when(clientRequestUser.getUserRolesPermissionsByToken(userToken))
                .thenReturn(userRolesPermissionsDto);

        final Authentication authenticationResponse = authenticationClientService.buildAuthentication(userToken);

        Assertions.assertThat(authenticationResponse.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .contains("permission::Read", "role::User");
    }
}
