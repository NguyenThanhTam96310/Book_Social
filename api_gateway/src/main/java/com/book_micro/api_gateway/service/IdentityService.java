package com.book_micro.api_gateway.service;

import org.springframework.stereotype.Service;

import com.book_micro.api_gateway.dto.request.IntrospectRequest;
import com.book_micro.api_gateway.dto.response.ApiResponse;
import com.book_micro.api_gateway.dto.response.IntrospectResponse;
import com.book_micro.api_gateway.repository.IdentityClient;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {

        return identityClient.introspect(
                IntrospectRequest.builder().token(token).build());
    }
}
