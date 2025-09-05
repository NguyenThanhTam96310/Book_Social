package com.book_micro.identity_service.mapper;

import org.mapstruct.Mapper;

import com.book_micro.identity_service.dto.request.PermissionRequest;
import com.book_micro.identity_service.dto.response.PermissionResponse;
import com.book_micro.identity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
