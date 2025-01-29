package com.klccgr.SchoolManagementSystem.dto;

import com.klccgr.SchoolManagementSystem.model.Classroom;
import com.klccgr.SchoolManagementSystem.model.Grade;
import com.klccgr.SchoolManagementSystem.model.Student;
import com.klccgr.SchoolManagementSystem.model.Teacher;

import java.util.Set;

public class CourseDTO {
    private Long id;
    private String courseName;
    private String semester;
    private int credit;
    private Set<Long> studentIds;
    private Long teacherId;
    private Set<Long> gradeIds;
    private Long classroomId;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String courseName, String semester, int credit, Set<Long> studentIds, Long teacherId, Set<Long> gradeIds, Long classroomId) {
        this.id = id;
        this.courseName = courseName;
        this.semester = semester;
        this.credit = credit;
        this.studentIds = studentIds;
        this.teacherId = teacherId;
        this.gradeIds = gradeIds;
        this.classroomId = classroomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Set<Long> getGradeIds() {
        return gradeIds;
    }

    public void setGradeIds(Set<Long> gradeIds) {
        this.gradeIds = gradeIds;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
}
