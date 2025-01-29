package com.klccgr.SchoolManagementSystem.controller;

import com.klccgr.SchoolManagementSystem.dto.StudentDTO;
import com.klccgr.SchoolManagementSystem.model.Student;
import com.klccgr.SchoolManagementSystem.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudent(@RequestParam Optional<Long> courseId, @RequestParam Optional<Long> departmentId) {
        return studentService.getAllStudent(courseId, departmentId);
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    public Student createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{studentId}")
    public Student updateStudentById(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudentById(studentId, studentDTO);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudentById(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
    }

}
