package com.klccgr.SchoolManagementSystem.service;

import com.klccgr.SchoolManagementSystem.dto.ClassroomDTO;
import com.klccgr.SchoolManagementSystem.model.Classroom;
import com.klccgr.SchoolManagementSystem.model.Course;
import com.klccgr.SchoolManagementSystem.repository.ClassroomRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    ClassroomRepository classroomRepository;
    CourseService courseService;

    public ClassroomService(ClassroomRepository classroomRepository, @Lazy CourseService courseService) {
        this.classroomRepository = classroomRepository;
        this.courseService = courseService;
    }

    public List<Classroom> getAllClassrooms(Optional<Long> courseId) {
        if (courseId.isPresent()) {
            return classroomRepository.findByCoursesId(courseId);
        } else {
            return classroomRepository.findAll();
        }
        //return classroomRepository.findAll().stream().map(classroom -> new ClassroomDTO(classroom.getId(), classroom.getRoomNumber(), classroom.getCapacity())).collect(Collectors.toList());
    }

    public Classroom getClassroomById(Long classroomId) {
        return classroomRepository.findById(classroomId).orElse(null);
    }

    public Classroom createClassroom(ClassroomDTO classroomDTO) {
        Set<Course> courses = getCourses(classroomDTO);
        Classroom newClassroom = new Classroom();
        newClassroom.setCapacity(classroomDTO.getCapacity());
        newClassroom.setRoomNumber(classroomDTO.getRoomNumber());
        if (!courses.isEmpty()) {
            setCourses(courses, newClassroom);
            newClassroom.setCourses(courses);
        }
        return classroomRepository.save(newClassroom);
    }

    public Classroom updateClassroom(Long classroomId, ClassroomDTO classroomDTO) {
        Optional<Classroom> currentClassroom = classroomRepository.findById(classroomId);
        Set<Course> courses = getCourses(classroomDTO);
        if (currentClassroom.isPresent()) {
            Classroom classroomToBeUpdated = currentClassroom.get();
            classroomToBeUpdated.setCapacity(classroomDTO.getCapacity());
            classroomToBeUpdated.setRoomNumber(classroomDTO.getRoomNumber());
            setCourses(courses, classroomToBeUpdated);
            classroomToBeUpdated.setCourses(courses);
            return classroomRepository.save(classroomToBeUpdated);
        }
        return null;
    }

    private Set<Course> getCourses(ClassroomDTO classroomDTO) {
        return classroomDTO.getCourseIds().stream()
                .map(courseId -> courseService.getCourseById(courseId)).collect(Collectors.toSet());
    }

    private void setCourses(Set<Course> courses, Classroom newClassroom) {
        for (Course course : courses) {
            course.setClassroom(newClassroom);
        }
    }

    public void deleteClassroomById(Long classroomId) {
        classroomRepository.deleteById(classroomId);
    }
}
