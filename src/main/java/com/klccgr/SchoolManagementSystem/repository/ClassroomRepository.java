package com.klccgr.SchoolManagementSystem.repository;

import com.klccgr.SchoolManagementSystem.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByCoursesId(Optional<Long> courseId);
}
