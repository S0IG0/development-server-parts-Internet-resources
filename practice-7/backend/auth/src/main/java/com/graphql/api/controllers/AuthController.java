package com.graphql.api.controllers;

import com.graphql.api.security.custom.models.User;
import com.graphql.api.security.httpTypes.JwtRefreshRequest;
import com.graphql.api.security.httpTypes.JwtRequest;
import com.graphql.api.security.httpTypes.JwtResponse;
import com.graphql.api.security.jwt.services.AuthService;
import com.graphql.api.security.jwt.services.JwtModelService;
import com.graphql.api.security.jwt.services.TokenService;
import com.graphql.api.security.jwt.utils.JwtUtils;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;

    }

//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @QueryMapping
//    public User getUserByRefreshToken(@Argument JwtRefreshRequest jwtRefreshRequest) throws AuthException {
//        return authService.getUserByRefreshToken(jwtRefreshRequest.getRefreshToken());
//    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws AuthException {
        return authService.getAccessAndRefreshTokens(jwtRequest);
    }
//
//    @MutationMapping
//    public JwtResponse refreshAccessToken(@Argument JwtRefreshRequest jwtRefreshRequest) throws AuthException {
//        return authService.getAccessToken(jwtRefreshRequest.getRefreshToken());
//    }
//
//    @MutationMapping
//    public String logout(@Argument String refreshToken) throws AuthException {
//        authService.disableJwtByRefreshToken(refreshToken);
//        return "disabled this session";
//    }
//
//    @MutationMapping
//    public String logoutAllSessions(@Argument String refreshToken) throws AuthException {
//        authService.disableAllJwtByRefreshToken(refreshToken);
//        return "disabled all sessions";
//    }
}
