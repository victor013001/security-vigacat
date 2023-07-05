package com.vigacat.security.service.component.security;

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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserPersistenceImpl userPersistence;

    @Test
    public void buildAuthentication() {
        String usernameVictor = "victor";
        String tokenVictor = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        LocalDateTime tokenVictorExpiresAt = LocalDateTime.now().plus(60L, ChronoUnit.MINUTES);

        final TokenDto tokenDtoVictor = TokenDto.builder()
                .username(usernameVictor)
                .token(tokenVictor)
                .expiresAt(tokenVictorExpiresAt)
                .build();

        final PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission("Read")
                .build();

        final PermissionDto permissionDtoCreate = PermissionDto.builder()
                .permission("Create")
                .build();


        final RoleDto roleDtoAdmin = RoleDto.builder()
                .name("Admin")
                .permissions(List.of(permissionDtoRead, permissionDtoCreate))
                .build();

        final UserDto userDtoVictor = UserDto.builder()
                .name(usernameVictor)
                .roles(List.of(roleDtoAdmin))
                .build();

        Mockito.when(userPersistence.getUserByUsernameAndApp(usernameVictor, 1L))
                .thenReturn(userDtoVictor);


        final Authentication authenticationVictor = authenticationService.buildAuthentication(tokenDtoVictor, 1L);

        Mockito.verify(userPersistence)
                .getUserByUsernameAndApp(usernameVictor, 1L);

        Assertions.assertThat(authenticationVictor.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .contains(
                        "role::Admin",
                        "permission::Create",
                        "permission::Read"
                );
    }
}
