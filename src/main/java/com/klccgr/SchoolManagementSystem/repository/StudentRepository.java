package com.klccgr.SchoolManagementSystem.repository;

import com.klccgr.SchoolManagementSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByCoursesIdAndDepartmentId(Long courseId, Long departmentId);

    List<Student> findByDepartmentId(Long departmentId);

    List<Student> findByCoursesId(Long courseId);
}
