package com.vigacat.security.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION_NAME = "Authorization";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION_NAME);

        //Extract the token
        //Validate token
        //Get User Details by app ID
        //Add details to Security Context Holder
        filterChain.doFilter(request, response);
    }
}
