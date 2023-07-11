package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.*;
import com.vigacat.security.service.component.security.TokenService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserPersistence userPersistence;

    @Test
    public void getUser() {
        Mockito.when(userPersistence.getUserByUsernameAndApp(
                "victor",
                1L
        )).thenReturn(UserDto.builder().name("victor").email("victor@gmail.com").build());
        final UserDto userDtoResponse = userService.getUser("victor",
                1L);
        Mockito.verify(userPersistence)
                .getUserByUsernameAndApp(
                        "victor",
                        1L
                );
        Assertions.assertThat(userDtoResponse).hasFieldOrPropertyWithValue("name", "victor").hasFieldOrPropertyWithValue(
                "email", "victor@gmail.com");
    }

    @Test
    public void getUserByUsername() {

        String username = "user";
        String password = "$2a$10$Ne7NBZi5AH6zZaAanNI.m.EycvsXq.B/scmHvNllz4NNSX9wvGPke";

        UsernamePasswordDto usernamePasswordDto = UsernamePasswordDto.builder()
                .name(username)
                .password(password)
                .build();

        Mockito.when(userPersistence.getUserByUsername(username))
                .thenReturn(Optional.of(usernamePasswordDto));

        final Optional<UsernamePasswordDto> userResponse = userService.getUserByUsername(username);

        Mockito.verify(userPersistence)
                .getUserByUsername(username);

        Assertions.assertThat(userResponse).get()
                .hasFieldOrPropertyWithValue("name", username)
                .hasFieldOrPropertyWithValue("password", password);

    }

    @Test
    public void createNewUser() {

        String username = "user";
        String userEmail = "user@email.com";
        String userPassword = "password";
        String userPasswordEncoded = "$2a$10$Ne7NBZi5AH6zZaAanNI.m.EycvsXq.B/scmHvNllz4NNSX9wvGPke";
        String tokenAdmin = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        String tokenUserNameAdmin = "Admin";


        List<RoleDto> userRolesDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .build(),
                RoleDto.builder()
                        .id(2L)
                        .build()
        );

        UserToSaveDto userToSaveDto = UserToSaveDto.builder()
                .name(username)
                .email(userEmail)
                .password(userPassword)
                .roles(userRolesDtos)
                .build();

        List<UserDto> emptyList = List.of();

        List<Long> roleIds = List.of(1L, 2L);

        List<RoleDto> roleDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .build(),
                RoleDto.builder()
                        .id(2L)
                        .build()
        );

        TokenDto tokenAdminDto = TokenDto.builder()
                .token(tokenAdmin)
                .username(tokenUserNameAdmin)
                .build();

        UserDto userDto = UserDto.builder()
                .name(username)
                .email(userEmail)
                .roles(userRolesDtos)
                .build();

        Mockito.when(userPersistence.getUsersByNameOrEmail(username, userEmail))
                .thenReturn(emptyList);

        Mockito.when(roleService.getRolesByIds(roleIds))
                .thenReturn(roleDtos);

        Mockito.when(passwordEncoder.encode(userPassword))
                .thenReturn(userPasswordEncoded);

        Mockito.when(tokenService.getValidToken(tokenAdmin))
                .thenReturn(tokenAdminDto);

        Mockito.when(userPersistence.saveNewUser(userToSaveDto, tokenUserNameAdmin))
                .thenReturn(userDto);

        final UserDto userDtoResponse = userService.createNewUser(userToSaveDto, tokenAdmin);

        Mockito.verify(userPersistence)
                .getUsersByNameOrEmail(username, userEmail);

        Mockito.verify(roleService)
                .getRolesByIds(roleIds);

        Mockito.verify(passwordEncoder)
                .encode(userPassword);

        Mockito.verify(tokenService)
                .getValidToken(tokenAdmin);

        Assertions.assertThat(userDtoResponse)
                .hasFieldOrPropertyWithValue("name", username)
                .hasFieldOrPropertyWithValue("email", userEmail)
                .hasFieldOrPropertyWithValue("roles", roleDtos);
    }

    @Test
    public void test() {
        String string = "hola";
        String string2 = new String("hola");
        String string3 = "hola";

        Assertions.assertThat(string.equals(string2)).isTrue();
        Assertions.assertThat(string == string2).isFalse();
        Assertions.assertThat(string == string3).isTrue();

    }
}
