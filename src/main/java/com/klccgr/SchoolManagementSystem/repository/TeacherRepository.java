package com.klccgr.SchoolManagementSystem.repository;

import com.klccgr.SchoolManagementSystem.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    List<Teacher> findBycoursesIdAndDepartmentId(Long courseId, Long departmentId);

    List<Teacher> findBycoursesId(Long courseId);

    List<Teacher> findBydepartmentId(Long departmentId);
}
