package com.klccgr.SchoolManagementSystem.service;

import com.klccgr.SchoolManagementSystem.dto.StudentDTO;
import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.model.Department;
import com.klccgr.SchoolManagementSystem.model.Student;
import com.klccgr.SchoolManagementSystem.repository.StudentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    StudentRepository studentRepository;
    DepartmentService departmentService;
    CourseService courseService;

    public StudentService(StudentRepository studentRepository, @Lazy DepartmentService departmentService, @Lazy CourseService courseService) {
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
        this.courseService = courseService;
    }

    public List<Student> getAllStudent(Optional<Long> courseId, Optional<Long> departmentId) {
        if (courseId.isPresent() && departmentId.isPresent()) {
            return studentRepository.findByCoursesIdAndDepartmentId(courseId.get(), departmentId.get());
            /*return studentRepository.findByCourseIdAndDepartmentId(courseId.get(), departmentId.get()).stream()
                    .map(student -> new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getPhoneNumber(), student.getAddress()))
                    .collect(Collectors.toList());*/
        }
        if (departmentId.isPresent()) {
            return studentRepository.findByDepartmentId(departmentId.get());
        } else if (courseId.isPresent()) {
            return studentRepository.findByCoursesId(courseId.get());
        } else {
            return studentRepository.findAll();
        }
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public Student createStudent(StudentDTO studentDTO) { //TODO custom exception ekle
        StudentInfo studentInfo = getDepartmentAndCourses(studentDTO);
        if (studentInfo.department != null && !studentInfo.courses.isEmpty()) {
            Student newStudent = new Student();
            newStudent.setDepartment(studentInfo.department);
            newStudent.setCourses(studentInfo.courses);
            newStudent.setEmail(studentDTO.getEmail());
            newStudent.setFirstName(studentDTO.getFirstName());
            newStudent.setLastName(studentDTO.getLastName());
            newStudent.setPhoneNumber(studentDTO.getPhoneNumber());
            newStudent.setAddress(studentDTO.getAddress());

            return studentRepository.save(newStudent);
        }
        return null;
    }

    public Student updateStudentById(Long studentId, StudentDTO studentDTO) {
        Optional<Student> currentStudent = studentRepository.findById(studentId);
        if (currentStudent.isPresent()) {
            Student studentToBeUpdated = currentStudent.get();
            studentToBeUpdated.setFirstName(studentDTO.getFirstName());
            studentToBeUpdated.setLastName(studentDTO.getLastName());
            studentToBeUpdated.setEmail(studentDTO.getEmail());
            studentToBeUpdated.setAddress(studentDTO.getAddress());
            studentToBeUpdated.setPhoneNumber(studentDTO.getPhoneNumber());
            StudentInfo studentInfo = getDepartmentAndCourses(studentDTO);
            studentToBeUpdated.setCourses(studentInfo.courses);
            studentToBeUpdated.setDepartment(studentInfo.department);
            return studentRepository.save(studentToBeUpdated);
        }
        return null;
    }

    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    private StudentInfo getDepartmentAndCourses(StudentDTO studentDTO) {
        Set<Course> courses = studentDTO.getCourseIds().stream()
                .map(courseId -> courseService.getCourseById(courseId)).collect(Collectors.toSet());
        Department department = departmentService.getDepartmentById(studentDTO.getDepartmentId());
        return new StudentInfo(courses, department);
    }

    public class StudentInfo {
        Set<Course> courses;
        Department department;

        public StudentInfo(Set<Course> courses, Department department) {
            this.courses = courses;
            this.department = department;
        }
    }
}
