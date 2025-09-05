package com.book_micro.identity_service.mapper;

import org.mapstruct.Mapper;

import com.book_micro.identity_service.dto.request.ProfileCreationRequest;
import com.book_micro.identity_service.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
