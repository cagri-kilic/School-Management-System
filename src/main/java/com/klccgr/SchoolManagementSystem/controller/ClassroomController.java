package com.klccgr.SchoolManagementSystem.controller;

import com.klccgr.SchoolManagementSystem.dto.ClassroomDTO;
import com.klccgr.SchoolManagementSystem.model.Classroom;
import com.klccgr.SchoolManagementSystem.service.ClassroomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<Classroom> getAllClassrooms(@RequestParam Optional<Long> courseId) {
        return classroomService.getAllClassrooms(courseId);
    }

    @GetMapping("/{classroomId}")
    public Classroom getClassroomById(@PathVariable Long classroomId) {
        return classroomService.getClassroomById(classroomId);
    }

    @PostMapping
    public Classroom createClassroom(@RequestBody ClassroomDTO classroomDTO) {
        return classroomService.createClassroom(classroomDTO);
    }

    @PutMapping("/{classroomId}")
    public Classroom updateClassroom(@PathVariable Long classroomId, @RequestBody ClassroomDTO classroomDTO) {
        return classroomService.updateClassroom(classroomId, classroomDTO);
    }

    @DeleteMapping("/{classroomId}")
    public void deleteClassroomById(@PathVariable Long classroomId) {
        classroomService.deleteClassroomById(classroomId);
    }
}
