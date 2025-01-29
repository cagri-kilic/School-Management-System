package com.klccgr.SchoolManagementSystem.repository;

import com.klccgr.SchoolManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByTeachersIdAndStudentsId(Long teacherId, Long studentId);

    List<Department> findByTeachersId(Long teacherId);

    List<Department> findByStudentsId(Long studentId);
}
