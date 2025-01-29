package com.klccgr.SchoolManagementSystem.service;

import com.klccgr.SchoolManagementSystem.dto.TeacherDTO;
import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.model.Department;
import com.klccgr.SchoolManagementSystem.model.Teacher;
import com.klccgr.SchoolManagementSystem.repository.TeacherRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    TeacherRepository teacherRepository;
    CourseService courseService;
    DepartmentService departmentService;

    public TeacherService(TeacherRepository teacherRepository, @Lazy CourseService courseService, @Lazy DepartmentService departmentService) {
        this.teacherRepository = teacherRepository;
        this.courseService = courseService;
        this.departmentService = departmentService;
    }


    public List<Teacher> getAllTeachers(Optional<Long> courseId, Optional<Long> departmentId) {
        if (courseId.isPresent() && departmentId.isPresent()) {
            return teacherRepository.findBycoursesIdAndDepartmentId(courseId.get(), departmentId.get());
        } else if (courseId.isPresent()) {
            return teacherRepository.findBycoursesId(courseId.get());
        } else if (departmentId.isPresent()) {
            return teacherRepository.findBydepartmentId(departmentId.get());
        } else {
            return teacherRepository.findAll();
        }
        /*return teacherRepository.findAll().stream()
                .map(teacher -> new TeacherDTO(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(), teacher.getPhoneNumber(), teacher.getAddress()))
                .collect(Collectors.toList());*/
    }

    public Teacher getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    public Teacher createTeacher(TeacherDTO teacherDTO) {
        TeacherInfo teacherInfo = getTeacherInfo(teacherDTO);
        if (!teacherInfo.courses.isEmpty() && teacherInfo.department != null) {
            Teacher newTeacher = new Teacher();
            newTeacher.setFirstName(teacherDTO.getFirstName());
            newTeacher.setLastName(teacherDTO.getLastName());
            newTeacher.setEmail(teacherDTO.getEmail());
            newTeacher.setPhoneNumber(teacherDTO.getPhoneNumber());
            newTeacher.setAddress(teacherDTO.getAddress());
            setCourses(teacherInfo.courses, newTeacher);
            newTeacher.setDepartment(teacherInfo.department);
            newTeacher.setCourses(teacherInfo.courses);
            return teacherRepository.save(newTeacher);
        }
        return null;
    }

    public Teacher updateTeacher(Long teacherId, TeacherDTO teacherDTO) {
        Optional<Teacher> currentTeacher = teacherRepository.findById(teacherId);
        TeacherInfo teacherInfo = getTeacherInfo(teacherDTO);
        if (currentTeacher.isPresent()) {
            Teacher teacherToBeUpdated = currentTeacher.get();
            teacherToBeUpdated.setFirstName(teacherDTO.getFirstName());
            teacherToBeUpdated.setLastName(teacherDTO.getLastName());
            teacherToBeUpdated.setEmail(teacherDTO.getEmail());
            teacherToBeUpdated.setPhoneNumber(teacherDTO.getPhoneNumber());
            teacherToBeUpdated.setAddress(teacherDTO.getAddress());
            setCourses(teacherInfo.courses, teacherToBeUpdated);
            teacherToBeUpdated.setDepartment(teacherInfo.department);
            teacherToBeUpdated.setCourses(teacherInfo.courses);
            return teacherRepository.save(teacherToBeUpdated);
        }
        return null;
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    private TeacherInfo getTeacherInfo(TeacherDTO teacherDTO) {
        Department department = departmentService.getDepartmentById(teacherDTO.getDepartmentId());
        Set<Course> courses = teacherDTO.getCourseIds().stream().map(courseId -> courseService.getCourseById(courseId)).collect(Collectors.toSet());
        return new TeacherInfo(courses, department);
    }

    private void setCourses(Set<Course> courses, Teacher teacher) {
        for (Course course : courses) {
            course.setTeacher(teacher);
        }
    }

    public class TeacherInfo {
        Set<Course> courses;
        Department department;

        public TeacherInfo(Set<Course> courses, Department department) {
            this.courses = courses;
            this.department = department;
        }
    }
}
