package com.book_micro.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.book_micro.identity_service.dto.request.RoleRequest;
import com.book_micro.identity_service.dto.response.RoleResponse;
import com.book_micro.identity_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // Mình không ánh xạ permissions từ RoleRequest sang Role vì nó là
    // Set<String> và không phải là một entity
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
