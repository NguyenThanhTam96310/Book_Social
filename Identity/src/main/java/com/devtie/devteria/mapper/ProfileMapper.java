package com.devtie.devteria.mapper;

import org.mapstruct.Mapper;

import com.devtie.devteria.dto.request.ProfileCreationRequest;
import com.devtie.devteria.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
