package com.klccgr.SchoolManagementSystem.service;

import com.klccgr.SchoolManagementSystem.dto.GradeDTO;
import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.model.Grade;
import com.klccgr.SchoolManagementSystem.model.Student;
import com.klccgr.SchoolManagementSystem.repository.GradeRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeService {

    GradeRepository gradeRepository;
    StudentService studentService;
    CourseService courseService;

    public GradeService(GradeRepository gradeRepository, @Lazy StudentService studentService, @Lazy CourseService courseService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<Grade> getAllGrades(Optional<Long> studentId, Optional<Long> courseId) {
        if (studentId.isPresent() && courseId.isPresent()) {
            return gradeRepository.findByStudentIdAndCourseId(studentId.get(), courseId.get());
        } else if(studentId.isPresent()) {
            return gradeRepository.findByStudentId(studentId.get());
        } else if(courseId.isPresent()) {
            return gradeRepository.findByCourseId(courseId.get());
        } else {
            return gradeRepository.findAll();
        }
        //return gradeRepository.findAll().stream().map(grade -> new GradeDTO(grade.getId(), grade.getGradeValue())).collect(Collectors.toList());
    }

    public Grade getGradeById(Long gradeId) {
        return gradeRepository.findById(gradeId).orElse(null);
    }

    public Grade createGrade(GradeDTO gradeDTO) {
        GradeInfo gradeInfo = getGradeInfo(gradeDTO);
        if(gradeInfo.course != null && gradeInfo.student != null) {
            Grade newGrade = new Grade();
            newGrade.setGradeValue(gradeDTO.getGradeValue());
            newGrade.setCourse(gradeInfo.course);
            newGrade.setStudent(gradeInfo.student);
            return gradeRepository.save(newGrade);
        }
        return null;
    }

    public Grade updateGrade(Long gradeId, GradeDTO gradeDTO) {
        Optional<Grade> currentGrade = gradeRepository.findById(gradeId);
        if (currentGrade.isPresent()) {
            Grade gradeToBeUpdated = currentGrade.get();
            gradeToBeUpdated.setGradeValue(gradeDTO.getGradeValue());
            GradeInfo gradeInfo = getGradeInfo(gradeDTO);
            gradeToBeUpdated.setCourse(gradeInfo.course);
            gradeToBeUpdated.setStudent(gradeInfo.student);
            return gradeRepository.save(gradeToBeUpdated);
        }
        return null;
    }

    public void deleteGradeById(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    private GradeInfo getGradeInfo(GradeDTO gradeDTO){
        Student student = studentService.getStudentById(gradeDTO.getStudentId());
        Course course = courseService.getCourseById(gradeDTO.getCourseId());
        return new GradeInfo(student, course);
    }

    public class GradeInfo{
        Student student;
        Course course;

        public GradeInfo(Student student, Course course) {
            this.student = student;
            this.course = course;
        }
    }
}
