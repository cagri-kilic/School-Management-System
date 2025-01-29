package com.klccgr.SchoolManagementSystem.controller;

import com.klccgr.SchoolManagementSystem.dto.DepartmentDTO;
import com.klccgr.SchoolManagementSystem.model.Department;
import com.klccgr.SchoolManagementSystem.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments(@RequestParam Optional<Long> teacherId, @RequestParam Optional<Long> studentId) {
        return departmentService.getAllDepartments(teacherId, studentId);
    }

    @GetMapping("/{departmentId}")
    public Department getDepartmentById(@PathVariable Long departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @PostMapping
    public Department createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @PutMapping("/{departmentId}")
    public Department updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.updateDepartment(departmentId, departmentDTO);
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartmentById(@PathVariable Long departmentId) {
        departmentService.deleteDepartmentById(departmentId);
    }
}
