package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

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
    final UserDto userDtoResponse =  userService.getUser("victor",
        1L);
    Mockito.verify(userPersistence)
           .getUserByUsernameAndApp(
               "victor",
               1L
           );
    Assertions.assertThat(userDtoResponse).hasFieldOrPropertyWithValue("name", "Victor").hasFieldOrPropertyWithValue(
        "email","victor@gmail.com");
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
