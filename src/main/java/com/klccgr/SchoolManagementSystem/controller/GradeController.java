package com.klccgr.SchoolManagementSystem.controller;

import com.klccgr.SchoolManagementSystem.dto.GradeDTO;
import com.klccgr.SchoolManagementSystem.model.Grade;
import com.klccgr.SchoolManagementSystem.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("grades")
public class GradeController {

    GradeService gradeService;
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<Grade> getAllGrades(@RequestParam Optional<Long> studentId, @RequestParam Optional<Long> courseId) {
        return gradeService.getAllGrades(studentId, courseId);
    }

    @GetMapping("/{gradeId}")
    public Grade getGradeById(@PathVariable Long gradeId){
        return gradeService.getGradeById(gradeId);
    }

    @PostMapping
    public Grade createGrade(@RequestBody GradeDTO gradeDTO){
        return gradeService.createGrade(gradeDTO);
    }

    @PutMapping("/{gradeId}")
    public Grade updateGrade(@PathVariable Long gradeId, @RequestBody GradeDTO gradeDTO){
        return gradeService.updateGrade(gradeId, gradeDTO);
    }

    @DeleteMapping("/{gradeId}")
    public void deleteGrade(@PathVariable Long gradeId){
        gradeService.deleteGradeById(gradeId);
    }
}
