package com.book_micro.profile_service.mapper;

import org.mapstruct.Mapper;

import com.book_micro.profile_service.dto.request.UserProfileCreationRequest;
import com.book_micro.profile_service.dto.response.UserProfileResponse;
import com.book_micro.profile_service.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
