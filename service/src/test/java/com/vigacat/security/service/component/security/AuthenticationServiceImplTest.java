package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.component.PermissionPersistence;
import com.vigacat.security.persistence.component.UserPersistenceImpl;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.persistence.dto.UserDto;
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
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserPersistenceImpl userPersistence;
    @Mock
    private PermissionPersistence permissionPersistence;

    @Test
    public void buildAuthentication() {
        String usernameVictor = "victor";
        String tokenVictor = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";

        final TokenDto tokenDtoVictor = TokenDto.builder()
                .username(usernameVictor)
                .token(tokenVictor)
                .build();

        final PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission("Read")
                .build();

        final PermissionDto permissionDtoCreate = PermissionDto.builder()
                .permission("Create")
                .build();


        final RoleDto roleDtoAdmin = RoleDto.builder()
                .id(1L)
                .name("Admin")
                .permissions(List.of(
                        permissionDtoRead,
                        permissionDtoCreate))
                .build();

        final UserDto userDtoVictor = UserDto.builder()
                .name(usernameVictor)
                .roles(List.of(roleDtoAdmin))
                .build();

        final List<Long> roleIds = List.of(1L);

        final List<PermissionDto> permissionDtoList = List.of(
                permissionDtoRead,
                permissionDtoCreate
        );

        Mockito.when(userPersistence.getUserByUsernameAndApp(usernameVictor, 1L))
                .thenReturn(userDtoVictor);

        Mockito.when(permissionPersistence.getPermissionsByRoleIds(roleIds))
                .thenReturn(permissionDtoList);


        final Authentication authenticationVictor = authenticationService.buildAuthentication(tokenDtoVictor, 1L);

        Mockito.verify(userPersistence)
                .getUserByUsernameAndApp(usernameVictor, 1L);

        Mockito.verify(permissionPersistence)
                        .getPermissionsByRoleIds(roleIds);

        Assertions.assertThat(authenticationVictor.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .contains(
                        "role::Admin",
                        "permission::Create",
                        "permission::Read"
                );
    }

}
