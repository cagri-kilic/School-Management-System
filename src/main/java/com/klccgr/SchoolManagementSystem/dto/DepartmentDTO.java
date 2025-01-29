package com.klccgr.SchoolManagementSystem.dto;

import java.util.Set;

public class DepartmentDTO {
    private Long id;
    private String departmentName;
    private Set<Long> teacherIds;
    private Set<Long> studentIds;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String departmentName, Set<Long> teacherIds, Set<Long> studentIds) {
        this.id = id;
        this.departmentName = departmentName;
        this.teacherIds = teacherIds;
        this.studentIds = studentIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Long> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(Set<Long> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }
}

