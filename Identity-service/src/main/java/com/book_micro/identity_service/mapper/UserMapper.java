package com.book_micro.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.book_micro.identity_service.dto.request.UserCreationRequest;
import com.book_micro.identity_service.dto.request.UserUpdateRequest;
import com.book_micro.identity_service.dto.response.UserResponse;
import com.book_micro.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    // @Mapping(source = "firstName", target = "lastName") // Khi get thif sẽ lấy
    // lastName từ firstName
    // @Mapping(target = "lastName", ignore = true) // khoong lấy lastName từ ra khi
    // đó lastName sẽ là null
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
