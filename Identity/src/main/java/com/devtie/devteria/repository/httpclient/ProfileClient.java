package com.devtie.devteria.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devtie.devteria.dto.request.ProfileCreationRequest;
import com.devtie.devteria.dto.response.UserProfileResponse;

@FeignClient(name = "profile-service", url = "${app.services.profile}")
public interface ProfileClient {
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request);
}
