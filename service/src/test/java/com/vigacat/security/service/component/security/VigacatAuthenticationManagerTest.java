package com.vigacat.security.service.component.security;

import com.vigacat.security.service.component.spring.VigacatUserDetailService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class VigacatAuthenticationManagerTest {

    @InjectMocks
    private VigacatAuthenticationManager vigacatAuthenticationManager;

    @Mock
    private VigacatUserDetailService vigacatUserDetailService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void authenticate() {

        String username = "user";
        String userPassword = "dummy";
        String userPasswordEncode = "$2a$10$.uBLRQjhcB5ci.0AZyVpMeJblzjzINI362z6WZLov0pkcC/gVtc12";

        List<GrantedAuthority> userAuthorities = List.of(
                new SimpleGrantedAuthority("LOGIN")
        );

        UserDetails userDetails = User.builder()
                .username(username)
                .password(userPasswordEncode)
                .authorities("LOGIN")
                .build();

        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(username,userPassword);
        Authentication userAuthenticationExpected = new UsernamePasswordAuthenticationToken(username,userPasswordEncode,userAuthorities);

        Mockito.when(vigacatUserDetailService.loadUserByUsername(username))
                .thenReturn(userDetails);

        Mockito.when(passwordEncoder.matches(userPassword, userPasswordEncode))
                .thenReturn(true);

        final Authentication userAuthenticationResponse = vigacatAuthenticationManager.authenticate(userAuthentication);

        Mockito.verify(vigacatUserDetailService)
                .loadUserByUsername(username);

        Mockito.verify(passwordEncoder)
                .matches(userPassword,userPasswordEncode);

        Assertions.assertThat(userAuthenticationResponse)
                .isEqualTo(userAuthenticationExpected);
    }

}
