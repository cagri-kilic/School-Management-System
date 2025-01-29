package com.klccgr.SchoolManagementSystem.dto;

import jakarta.persistence.Column;

import java.util.Set;

public class ClassroomDTO {
    private Long id;
    private String roomNumber;
    private int capacity;
    private Set<Long> courseIds;

    public ClassroomDTO() {
    }

    public ClassroomDTO(Long id, String roomNumber, int capacity, Set<Long> courseIds) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.courseIds = courseIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(Set<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
