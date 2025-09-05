package com.book_micro.identity_service.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book_micro.identity_service.dto.request.AuthenticationRequest;
import com.book_micro.identity_service.dto.request.IntrospectRequest;
import com.book_micro.identity_service.dto.request.LogoutRequest;
import com.book_micro.identity_service.dto.request.RefreshRequest;
import com.book_micro.identity_service.dto.response.ApiResponse;
import com.book_micro.identity_service.dto.response.AuthenticationResponse;
import com.book_micro.identity_service.dto.response.IntrospectResponse;
import com.book_micro.identity_service.service.AuthencationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // gán mặc định cho các trường là final và private
public class AuthenticatitionController {
    AuthencationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var isAuthenticated = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(isAuthenticated)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
            throws JOSEException, ParseException {
        var isAuthenticated = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(isAuthenticated)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        var isAuthenticated = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(isAuthenticated).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
