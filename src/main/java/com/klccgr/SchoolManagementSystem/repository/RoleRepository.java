package com.klccgr.SchoolManagementSystem.repository;

import com.klccgr.SchoolManagementSystem.enums.ERole;
import com.klccgr.SchoolManagementSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
