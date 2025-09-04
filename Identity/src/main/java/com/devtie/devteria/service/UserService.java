package com.devtie.devteria.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devtie.devteria.constant.PredefinedRole;
import com.devtie.devteria.dto.request.UserCreationRequest;
import com.devtie.devteria.dto.request.UserUpdateRequest;
import com.devtie.devteria.dto.response.UserResponse;
import com.devtie.devteria.entity.Role;
import com.devtie.devteria.entity.User;
import com.devtie.devteria.exception.AppException;
import com.devtie.devteria.exception.ErrorCode;
import com.devtie.devteria.mapper.ProfileMapper;
import com.devtie.devteria.mapper.UserMapper;
import com.devtie.devteria.repository.RoleRepository;
import com.devtie.devteria.repository.UserRepository;
import com.devtie.devteria.repository.httpclient.ProfileClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor // Tạo constructor tự động với tất cả các trường thay cho @Autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // gán mặc định cho các trường là final và private
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    ProfileClient profileClient;
    ProfileMapper profileMapper;

    public UserResponse createUser(UserCreationRequest request) {

        User user = userMapper.toUser(request); // thay cho tất cả các trường hợp set trực tiếp thay cho phần ở dưới

        user.setPassword(encoder.encode(user.getPassword())); // mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(role -> roles.add(role));
        user.setRoles(roles);

        try {
            user = userRepository.save(user);
            var profileRequest = profileMapper.toProfileCreationRequest(request);
            profileRequest.setUserId(user.getId());

            profileClient.createProfile(profileRequest);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        return userMapper.toUserResponse(user); // sử dụng mapper để chuyển đổi sang UserResponse
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method getUsers");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse) // sử dụng mapper để chuyển đổi sang UserResponse
                .toList();
    }

    @PostAuthorize("returnObject.userName == authentication.name") // người dùng hiện tại mới có quyền truy cập
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
