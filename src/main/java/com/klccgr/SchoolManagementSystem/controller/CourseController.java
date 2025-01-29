package com.klccgr.SchoolManagementSystem.controller;

import com.klccgr.SchoolManagementSystem.dto.CourseDTO;
import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses(@RequestParam Optional<Long> teacherId, @RequestParam Optional<Long> classroomId, @RequestParam Optional<Long> studentId, @RequestParam Optional<Long> gradeId){
        return courseService.getAllCourses(teacherId, classroomId, studentId, gradeId);
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable Long courseId){
        return courseService.getCourseById(courseId);
    }

    @PostMapping
    public Course createCourse(@RequestBody CourseDTO courseDTO){
        return courseService.createCourse(courseDTO);
    }

    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO){
        return courseService.updateCourse(courseId, courseDTO);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourseById(courseId);
    }
}
