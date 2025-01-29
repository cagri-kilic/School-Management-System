package com.klccgr.SchoolManagementSystem.service;

import com.klccgr.SchoolManagementSystem.dto.CourseDTO;
import com.klccgr.SchoolManagementSystem.model.*;
import com.klccgr.SchoolManagementSystem.model.Specifacition.CourseSpecification;
import com.klccgr.SchoolManagementSystem.repository.CourseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    CourseRepository courseRepository;
    StudentService studentService;
    TeacherService teacherService;
    GradeService gradeService;
    ClassroomService classroomService;

    public CourseService(CourseRepository courseRepository, @Lazy StudentService studentService, @Lazy TeacherService teacherService, @Lazy GradeService gradeService, @Lazy ClassroomService classroomService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        this.classroomService = classroomService;
    }

    public List<Course> getAllCourses(@RequestParam Optional<Long> teacherId, @RequestParam Optional<Long> classroomId, @RequestParam Optional<Long> studentId, @RequestParam Optional<Long> gradeId) {
        Specification<Course> spec = Specification.where(null);

        if (teacherId.isPresent()) {
            spec = spec.and(CourseSpecification.filterByTeacherId(teacherId));
        }
        if (classroomId.isPresent()) {
            spec = spec.and(CourseSpecification.filterByClassroomId(classroomId));
        }
        if (studentId.isPresent()) {
            spec = spec.and(CourseSpecification.filterByStudentId(studentId));
        }
        if (gradeId.isPresent()) {
            spec = spec.and(CourseSpecification.filterByGradeId(gradeId));
        }

        return courseRepository.findAll(spec);
        /*return courses.stream()
                .map(course -> new CourseDTO(course.getId(), course.getCourseName(), course.getSemester(), course.getCredit(), course.getStudents().))
                .collect(Collectors.toList());*/

        /*return courseRepository.findAll().stream()
                .map(course -> new CourseDTO(course.getId(), course.getCourseName(), course.getSemester(), course.getCredit()))
                .collect(Collectors.toList());*/
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public Course createCourse(CourseDTO courseDTO) {
        CourseInfo courseInfo = getCourseInfo(courseDTO);
        if (!courseInfo.students.isEmpty() && courseInfo.teacher != null && courseInfo.classroom != null) {
            Course newCourse = new Course();
            newCourse.setCourseName(courseDTO.getCourseName());
            newCourse.setCredit(courseDTO.getCredit());
            newCourse.setSemester(courseDTO.getSemester());
            newCourse.setTeacher(courseInfo.teacher);
            newCourse.setClassroom(courseInfo.classroom);
            newCourse.setStudents(courseInfo.students);
            newCourse.setGrades(courseInfo.grades);
            return courseRepository.save(newCourse);
        }
        //return courseRepository.save(courseDTO);
        return null;
    }

    public Course updateCourse(Long courseId, CourseDTO courseDTO) {
        CourseInfo courseInfo = getCourseInfo(courseDTO);
        Optional<Course> currentCourse = courseRepository.findById(courseId);
        if (currentCourse.isPresent()) {
            Course courseToBeUpdated = currentCourse.get();
            courseToBeUpdated.setCourseName(courseDTO.getCourseName());
            courseToBeUpdated.setSemester(courseDTO.getSemester());
            courseToBeUpdated.setCredit(courseDTO.getCredit());
            courseToBeUpdated.setTeacher(courseInfo.teacher);
            courseToBeUpdated.setClassroom(courseInfo.classroom);
            courseToBeUpdated.setStudents(courseInfo.students);
            courseToBeUpdated.setGrades(courseInfo.grades);
            return courseRepository.save(courseToBeUpdated);
        }
        return null;
    }

    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public CourseInfo getCourseInfo(CourseDTO courseDTO) {
        Set<Student> students = courseDTO.getStudentIds().stream()
                .map(studentId -> studentService.getStudentById(studentId))
                .collect(Collectors.toSet());

        Teacher teacher = teacherService.getTeacherById(courseDTO.getTeacherId());

        Set<Grade> grades = courseDTO.getGradeIds().stream()
                .map(gradeId -> gradeService.getGradeById(gradeId))
                .collect(Collectors.toSet());

        Classroom classroom = classroomService.getClassroomById(courseDTO.getClassroomId());

        return new CourseInfo(students, teacher, grades, classroom);
    }


    public class CourseInfo {
        private Set<Student> students;
        private Teacher teacher;
        private Set<Grade> grades;
        private Classroom classroom;

        public CourseInfo(Set<Student> students, Teacher teacher, Set<Grade> grades, Classroom classroom) {
            this.students = students;
            this.teacher = teacher;
            this.grades = grades;
            this.classroom = classroom;
        }
    }
}
