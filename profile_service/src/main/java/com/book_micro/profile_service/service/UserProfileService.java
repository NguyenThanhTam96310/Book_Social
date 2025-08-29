package com.book_micro.profile_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.book_micro.profile_service.dto.request.UserProfileCreationRequest;
import com.book_micro.profile_service.dto.response.UserProfileResponse;
import com.book_micro.profile_service.entity.UserProfile;
import com.book_micro.profile_service.mapper.UserProfileMapper;
import com.book_micro.profile_service.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(UserProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("PROFILE_NOT_FOUND"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public List<UserProfileResponse> getAllProfiles() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toUserProfileResponse)
                .toList();
    }
}
