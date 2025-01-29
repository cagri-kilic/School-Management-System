package com.klccgr.SchoolManagementSystem.controller;

import com.klccgr.SchoolManagementSystem.dto.TeacherDTO;
import com.klccgr.SchoolManagementSystem.model.Teacher;
import com.klccgr.SchoolManagementSystem.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")

public class TeacherController {

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers(@RequestParam Optional<Long> courseId, @RequestParam Optional<Long> departmentId) {
        return teacherService.getAllTeachers(courseId, departmentId);
    }

    @GetMapping("/{teacherId}")
    public Teacher getTeacherById(@PathVariable Long teacherId) {
        return teacherService.getTeacherById(teacherId);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.createTeacher(teacherDTO);
    }

    @PutMapping("/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(teacherId, teacherDTO);
    }

    @DeleteMapping("/{teacherId}")
    public void deleteTeacherById(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }
}
