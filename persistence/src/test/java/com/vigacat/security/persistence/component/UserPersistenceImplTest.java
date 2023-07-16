package com.vigacat.security.persistence.component;

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
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceImplTest {

    @InjectMocks
    private UserPersistenceImpl userPersistence;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;


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
    public void userNameOrEmailExist() {
        String username = "user";
        String userEmail = "user@email.com";

        Mockito.when(userRepository.existsUsersByNameOrEmail(username, userEmail))
                .thenReturn(true);

        final boolean userExistResponse = userPersistence.userNameOrEmailExist(username, userEmail);

        Mockito.verify(userRepository)
                .existsUsersByNameOrEmail(username, userEmail);

        Assertions.assertThat(userExistResponse)
                .isTrue();
    }

    @Test
    public void saveNewUser() {

        String username = "user";
        String userEmail = "user@email.com";
        String userPasswordEncoded = "$2a$10$Ne7NBZi5AH6zZaAanNI.m.EycvsXq.B/scmHvNllz4NNSX9wvGPke";
        String usernameAdminAuthenticated = "Admin";

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

        User userToSave = modelMapper.map(userToSaveDto, User.class);

        Mockito.when(securityContext.getAuthentication())
                .thenReturn(authentication);

        Mockito.when(authentication.getName())
                .thenReturn(usernameAdminAuthenticated);

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(userToSave);

        SecurityContextHolder.setContext(securityContext);

        final UserDto userDtoResponse = userPersistence.saveNewUser(userToSaveDto);

        Mockito.verify(securityContext)
                .getAuthentication();

        Mockito.verify(authentication)
                .getName();

        Mockito.verify(userRepository)
                .save(Mockito.any(User.class));

        Assertions.assertThat(userDtoResponse)
                .hasFieldOrPropertyWithValue("name", username)
                .hasFieldOrPropertyWithValue("email", userEmail);

        Assertions.assertThat(userDtoResponse.getRoles())
                .extracting(RoleDto::getId)
                .contains(1L, 2L);
    }

}
