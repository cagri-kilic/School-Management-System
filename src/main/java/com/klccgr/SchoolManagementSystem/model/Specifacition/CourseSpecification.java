package com.klccgr.SchoolManagementSystem.model.Specifacition;

import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.model.Grade;
import com.klccgr.SchoolManagementSystem.model.Student;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;

public class CourseSpecification {

    public static Specification<Course> filterByTeacherId(Optional<Long> teacherId) {
        return (root, query, criteriaBuilder) -> {
            if (teacherId.isPresent()) {
                return criteriaBuilder.equal(root.get("teacher").get("id"), teacherId.get());
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Course> filterByClassroomId(Optional<Long> classroomId) {
        return (root, query, criteriaBuilder) -> {
            if (classroomId.isPresent()) {
                return criteriaBuilder.equal(root.get("classroom").get("id"), classroomId.get());
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Course> filterByStudentId(Optional<Long> studentId) {
        return (root, query, criteriaBuilder) -> {
            if (studentId.isPresent()) {
                Join<Course, Student> studentJoin = root.join("students");
                return criteriaBuilder.equal(studentJoin.get("id"), studentId.get());
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Course> filterByGradeId(Optional<Long> gradeId) {
        return (root, query, criteriaBuilder) -> {
            if (gradeId.isPresent()) {
                Join<Course, Grade> gradeJoin = root.join("grades");
                return criteriaBuilder.equal(gradeJoin.get("id"), gradeId.get());
            }
            return criteriaBuilder.conjunction();
        };
    }
}
