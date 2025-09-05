package com.book_micro.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book_micro.identity_service.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
