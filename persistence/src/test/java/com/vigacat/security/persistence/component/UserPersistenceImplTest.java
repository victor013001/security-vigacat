package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.entity.User;
import com.vigacat.security.dao.repository.UserRepository;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceImplTest {

    @InjectMocks
    private UserPersistenceImpl userPersistence;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;


    @Test
    public void getUserByUsernameAndApp() {

        String userNameVictor = "victor";
        Long appId = 1L;
        String emailVictor = "victor@gmail.com";

        User userVictor = User.builder()
                .name(userNameVictor)
                .email(emailVictor)
                .roles(null)
                .build();

        UserDto userVictorDto = UserDto.builder()
                .name(userNameVictor)
                .email(emailVictor)
                .roles(null)
                .build();

        Mockito.when(userRepository.findUserByUsernameAndAppId(userNameVictor, appId))
                .thenReturn(Optional.of(userVictor));

        Mockito.when(modelMapper.map(userVictor, UserDto.class))
                .thenReturn(userVictorDto);

        final UserDto userDtoVictor = userPersistence.getUserByUsernameAndApp(userNameVictor, appId);

        Mockito.verify(userRepository).findUserByUsernameAndAppId(
                userNameVictor,
                appId
        );

        Mockito.verify(modelMapper).map(
                userVictor,
                UserDto.class
        );

        Assertions.assertThat(userDtoVictor)
                .hasFieldOrPropertyWithValue("name", userNameVictor)
                .hasFieldOrPropertyWithValue("email", emailVictor)
                .hasFieldOrPropertyWithValue("roles", null);
    }

    @Test
    public void getUser() {

        String userNameVictor = "victor";
        Long appId = 1L;
        String emailVictor = "victor@gmail.com";

        User userVictor = User.builder()
                .name(userNameVictor)
                .email(emailVictor)
                .roles(null)
                .build();

        UserDto userVictorDto = UserDto.builder()
                .name(userNameVictor)
                .email(emailVictor)
                .roles(null)
                .build();

        Mockito.when(userRepository.findByNameAndRolesAppId(userNameVictor, appId))
                .thenReturn(Optional.of(userVictor));

        Mockito.when(modelMapper.map(userVictor, UserDto.class))
                .thenReturn(userVictorDto);

        final UserDto userDtoVictor = userPersistence.getUser(userNameVictor, appId);

        Mockito.verify(userRepository).findByNameAndRolesAppId(
                userNameVictor,
                appId
        );

        Mockito.verify(modelMapper).map(
                userVictor,
                UserDto.class
        );

        Assertions.assertThat(userDtoVictor)
                .hasFieldOrPropertyWithValue("name", userNameVictor)
                .hasFieldOrPropertyWithValue("email", emailVictor)
                .hasFieldOrPropertyWithValue("roles", null);
    }

    @Test
    public void saveNewUser() {

        String username = "user";
        String userEmail = "user@email.com";
        String userPasswordEncoded = "$2a$10$Ne7NBZi5AH6zZaAanNI.m.EycvsXq.B/scmHvNllz4NNSX9wvGPke";
        String usernameToken = "Admin";


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
                .password(userPasswordEncoded)
                .roles(userRolesDtos)
                .build();

        List<Role> userRoles = List.of(
                Role.builder()
                        .id(1L)
                        .build(),
                Role.builder()
                        .id(2L)
                        .build()
        );

        User userToSave = User.builder()
                .name(username)
                .password(userPasswordEncoded)
                .email(userEmail)
                .roles(userRoles)
                .build();

        UserDto userDto = UserDto.builder()
                .name(username)
                .email(userEmail)
                .roles(userRolesDtos)
                .build();

        Mockito.when(modelMapper.map(Mockito.any(UserToSaveDto.class), Mockito.eq(User.class)))
                .thenReturn(userToSave);

        Mockito.when(modelMapper.map(Mockito.any(User.class), Mockito.eq(UserDto.class)))
                .thenReturn(userDto);

        Mockito.when(userRepository.save(userToSave))
                .thenReturn(userToSave);

        final UserDto userDtoResponse = userPersistence.saveNewUser(userToSaveDto, usernameToken);

        Mockito.verify(modelMapper)
                .map(Mockito.any(UserToSaveDto.class), Mockito.eq(User.class));

        Mockito.verify(modelMapper)
                .map(Mockito.any(User.class), Mockito.eq(UserDto.class));

        Mockito.verify(userRepository)
                .save(userToSave);

        Assertions.assertThat(userDtoResponse)
                .hasFieldOrPropertyWithValue("name", username)
                .hasFieldOrPropertyWithValue("email", userEmail)
                .hasFieldOrPropertyWithValue("roles", userRolesDtos);
    }

}
