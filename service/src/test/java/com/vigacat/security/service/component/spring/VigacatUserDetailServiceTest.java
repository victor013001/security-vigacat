package com.vigacat.security.service.component.spring;

import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import com.vigacat.security.service.component.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class VigacatUserDetailServiceTest {

    @InjectMocks
    private VigacatUserDetailService vigacatUserDetailService;

    @Mock
    private UserService userService;

    @Test
    public void loadUserByUsername() {

        String userName = "user";
        String userPassword = "dummy";

        UsernamePasswordDto userDto = UsernamePasswordDto.builder()
                .name(userName)
                .password(userPassword)
                .build();

        UserDetails userDetails = User.builder()
                .username(userName)
                .password(userPassword)
                .authorities("LOGIN")
                .build();

        Mockito.when(userService.getUserByUsername(userName))
                .thenReturn(Optional.of(userDto));

        final UserDetails userDetailsResponse = vigacatUserDetailService.loadUserByUsername(userName);

        Mockito.verify(userService)
                .getUserByUsername(userName);

        Assertions.assertThat(userDetailsResponse)
                .isEqualTo(userDetails);
    }

}
