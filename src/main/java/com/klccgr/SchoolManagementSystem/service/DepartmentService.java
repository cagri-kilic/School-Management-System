package com.klccgr.SchoolManagementSystem.service;

import com.klccgr.SchoolManagementSystem.dto.DepartmentDTO;
import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.model.Department;
import com.klccgr.SchoolManagementSystem.model.Student;
import com.klccgr.SchoolManagementSystem.model.Teacher;
import com.klccgr.SchoolManagementSystem.repository.DepartmentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    DepartmentRepository departmentRepository;
    TeacherService teacherService;
    StudentService studentService;

    public DepartmentService(DepartmentRepository departmentRepository, @Lazy TeacherService teacherService, @Lazy StudentService studentService) {
        this.departmentRepository = departmentRepository;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }


    public List<Department> getAllDepartments(Optional<Long> teacherId, Optional<Long> studentId) {
        if(teacherId.isPresent() && studentId.isPresent()) {
            return departmentRepository.findByTeachersIdAndStudentsId(teacherId.get(), studentId.get());
        } else if (teacherId.isPresent()) {
            return departmentRepository.findByTeachersId(teacherId.get());
        } else if (studentId.isPresent()) {
            return departmentRepository.findByStudentsId(studentId.get());
        } else {
            return departmentRepository.findAll();
        }
        //return departmentRepository.findAll().stream().map(department -> new DepartmentDTO(department.getId(), department.getDepartmentName())).collect(Collectors.toList());
    }

    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElse(null);
    }

    public Department createDepartment(DepartmentDTO departmentDTO) {
        DepartmentInfo departmentInfo = getDepartmentInfo(departmentDTO);
        if(!departmentInfo.teachers.isEmpty() && !departmentInfo.students.isEmpty()) {
            Department newDepartment = new Department();
            newDepartment.setDepartmentName(departmentDTO.getDepartmentName());
            setTeachers(newDepartment, departmentInfo.teachers);
            setStudents(newDepartment, departmentInfo.students);
            newDepartment.setTeachers(departmentInfo.teachers);
            newDepartment.setStudents(departmentInfo.students);
            return departmentRepository.save(newDepartment);
        }
        return null;
    }

    public Department updateDepartment(Long departmentId, DepartmentDTO departmentDTO) {
        Optional<Department> currentDepartment = departmentRepository.findById(departmentId);
        DepartmentInfo departmentInfo = getDepartmentInfo(departmentDTO);
        if (currentDepartment.isPresent()) {
            Department departmentToBeUpdated = currentDepartment.get();
            departmentToBeUpdated.setDepartmentName(departmentDTO.getDepartmentName());
            setTeachers(departmentToBeUpdated, departmentInfo.teachers);
            setStudents(departmentToBeUpdated, departmentInfo.students);
            departmentToBeUpdated.setTeachers(departmentInfo.teachers);
            departmentToBeUpdated.setStudents(departmentInfo.students);
            return departmentRepository.save(departmentToBeUpdated);
        }
        return null;
    }

    public void deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    private DepartmentInfo getDepartmentInfo(DepartmentDTO departmentDTO) {
        Set<Teacher> teachers = departmentDTO.getTeacherIds().stream()
                .map(teacherId -> teacherService.getTeacherById(teacherId)).collect(Collectors.toSet());
        Set<Student> students = departmentDTO.getStudentIds().stream()
                .map(studentId -> studentService.getStudentById(studentId)).collect(Collectors.toSet());
        return new DepartmentInfo(teachers, students);
    }

    private void setTeachers(Department department, Set<Teacher> teachers) {
        for(Teacher teacher : teachers) {
            teacher.setDepartment(department);
        }
    }

    private void setStudents(Department department, Set<Student> students) {
        for(Student student : students) {
            student.setDepartment(department);
        }
    }

    public class DepartmentInfo{
        Set<Teacher> teachers;
        Set<Student> students;

        public DepartmentInfo(Set<Teacher> teachers, Set<Student> students) {
            this.teachers = teachers;
            this.students = students;
        }
    }
}
