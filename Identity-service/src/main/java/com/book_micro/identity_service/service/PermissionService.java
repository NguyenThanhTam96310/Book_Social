package com.book_micro.identity_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.book_micro.identity_service.dto.request.PermissionRequest;
import com.book_micro.identity_service.dto.response.PermissionResponse;
import com.book_micro.identity_service.entity.Permission;
import com.book_micro.identity_service.mapper.PermissionMapper;
import com.book_micro.identity_service.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor // Tạo constructor tự động với tất cả các trường thay cho @Autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
