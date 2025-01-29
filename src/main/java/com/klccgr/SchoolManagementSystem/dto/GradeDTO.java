package com.klccgr.SchoolManagementSystem.dto;

public class GradeDTO {
    private Long id;
    private Double gradeValue;
    private Long studentId;
    private Long courseId;

    public GradeDTO() {
    }

    public GradeDTO(Long id, Double gradeValue, Long studentId, Long courseId) {
        this.id = id;
        this.gradeValue = gradeValue;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
