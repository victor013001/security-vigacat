package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private RoleService roleService;
    @Spy
    private PasswordEncoder passwordEncoder;
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
        String usernameAdminAuthenticated = "userAdmin";

        List<Long> userRoleIds = List.of(1L, 2L);

        UserToSaveDto userToSaveDto = UserToSaveDto.builder()
                .name(username)
                .email(userEmail)
                .password(userPassword)
                .roleIds(userRoleIds)
                .build();

        UserDto userDto = UserDto.builder()
                .name(username)
                .email(userEmail)
                .build();

        Mockito.when(userPersistence.userNameOrEmailExist(username, userEmail))
                .thenReturn(false);

        Mockito.when(roleService.roleIdsExist(Mockito.anyList()))
                .thenReturn(true);

        Mockito.when(userPersistence.saveNewUser(userToSaveDto, usernameAdminAuthenticated))
                .thenReturn(userDto);

        final UserDto userDtoResponse = userService.createNewUser(userToSaveDto);

        Mockito.verify(userPersistence)
                .userNameOrEmailExist(username, userEmail);

        Mockito.verify(roleService)
                .roleIdsExist(Mockito.anyList());

        Assertions.assertThat(userDtoResponse)
                .hasFieldOrPropertyWithValue("name", username)
                .hasFieldOrPropertyWithValue("email", userEmail);
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
