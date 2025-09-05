package com.book_micro.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book_micro.event.dto.NotificationEvent;
import com.book_micro.identity_service.constant.PredefinedRole;
import com.book_micro.identity_service.dto.request.UserCreationRequest;
import com.book_micro.identity_service.dto.request.UserUpdateRequest;
import com.book_micro.identity_service.dto.response.UserResponse;
import com.book_micro.identity_service.entity.Role;
import com.book_micro.identity_service.entity.User;
import com.book_micro.identity_service.exception.AppException;
import com.book_micro.identity_service.exception.ErrorCode;
import com.book_micro.identity_service.mapper.ProfileMapper;
import com.book_micro.identity_service.mapper.UserMapper;
import com.book_micro.identity_service.repository.RoleRepository;
import com.book_micro.identity_service.repository.UserRepository;
import com.book_micro.identity_service.repository.httpclient.ProfileClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    ProfileClient profileClient;
    ProfileMapper profileMapper;
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request) {
        log.info("Service : Create user");
        User user = userMapper.toUser(request);

        user.setPassword(encoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(role -> roles.add(role));
        user.setRoles(roles);
        user.setEmailVerified(false);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());

        var profile = profileClient.createProfile(profileRequest);

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(request.getEmail())
                .subject("Welcome to Book Social")
                .boby("Hello " + request.getUsername())
                .build();

        // publish message to kafka
        kafkaTemplate.send("notification-delivery", notificationEvent);
        var userCreationReponse = userMapper.toUserResponse(user);
        userCreationReponse.setId(profile.getResult().getId());

        return userCreationReponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method getUsers");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getUserById(String userId) {
        log.info("In method getUserbyId with userId: {}", userId);
        return userMapper.toUserResponse(userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext(); // lấy thông tin người dùng từ SecurityContext
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user); // sử dụng mapper để chuyển đổi sang UserResponse
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);

        user.setPassword(encoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
