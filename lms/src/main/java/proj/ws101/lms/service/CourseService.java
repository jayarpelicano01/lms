package proj.ws101.lms.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.ws101.lms.entity.Course;
import proj.ws101.lms.entity.Enrollment;
import proj.ws101.lms.entity.User;
import proj.ws101.lms.repository.CourseRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(@Autowired CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourse(){
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id){
        return courseRepository.findById(id);
    }

    public void addCourse(Course course){
        courseRepository.save(course);
    }

    public void updateStatus(Long id){
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();
            if (course.getStatus().equalsIgnoreCase("published")){
                course.setStatus("unpublished");
                courseRepository.save(course);
            } else {
                course.setStatus("published");
                courseRepository.save(course);
            }

        } else {
            throw new EntityNotFoundException("Course with ID: " + id + "Not found");
        }
    }

    public List<User> getAllUserByCourse(Long id){
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();

            List<User> people = new ArrayList<>();

            people.add(course.getTeacher().getUser());
            for (Enrollment enrollment : course.getEnrollments()){
                people.add(enrollment.getUser());
            }
            return people;
        } else {
            throw new RuntimeException("Not Found");
        }
    }

    public void deleteCourseById(Long id){
        courseRepository.deleteById(id);
    }
}

