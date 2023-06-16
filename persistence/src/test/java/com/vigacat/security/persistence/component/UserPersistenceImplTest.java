package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.User;
import com.vigacat.security.dao.repository.UserRepository;
import com.vigacat.security.persistence.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceImplTest {

  @InjectMocks
  private UserPersistenceImpl userPersistence;

  @InjectMocks
  private UserDto userDto;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private UserRepository userRepository;


  @Test
  public void getUserByUsernameAndApp() {

    String userNameVictor = "victor";
    Long appId = 1L;
    String emailVictor= "victor@gmail.com";

    User userVictor = User.builder()
        .name(userNameVictor)
        .email(emailVictor)
        .roles(null)
        .build();

    UserDto userVictorDto = userDto.builder()
        .name(userNameVictor)
        .email(emailVictor)
        .roles(null)
        .build();

    Mockito.when(userRepository.findUserByUsernameAndAppId(userNameVictor, appId))
        .thenReturn(Optional.of(userVictor));

    Mockito.when(modelMapper.map(userVictor,UserDto.class))
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
    String emailVictor= "victor@gmail.com";

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

    Mockito.when(userRepository.findByNameAndRolesAppId(userNameVictor,appId))
        .thenReturn(Optional.of(userVictor));

    Mockito.when(modelMapper.map(userVictor,UserDto.class))
        .thenReturn(userVictorDto);

    final UserDto userDtoVictor = userPersistence.getUser(userNameVictor,appId);

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
        .hasFieldOrPropertyWithValue("roles",null);
  }

}
