package proj.ws101.lms.controller;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.ws101.lms.entity.*;
import proj.ws101.lms.repository.CourseRepository;
import proj.ws101.lms.repository.EnrollmentRepository;
import proj.ws101.lms.service.CourseService;
import proj.ws101.lms.service.UserService;

import javax.swing.text.html.Option;
import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    public final CourseService courseService;
    private final UserService userService;

    public CourseController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/{courseId}/people")
    public List<Enrollment> getEnrolledPeople(@PathVariable Long courseId) {
        // Assuming Enrollment entity has a reference to Student and Course
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return enrollmentRepository.findByCourse(course); // Query to get enrollments for the course
    }



    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourse();
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @GetMapping("/{id}/students")
    public List<User> getStudentsByCourse(@PathVariable Long id){
        Optional<Course> courseOpt = courseService.getCourseById(id);

        if (courseOpt.isPresent()){
            Course course = courseOpt.get();
            List<Enrollment> enrollments = course.getEnrollments();
            List<User> students = new ArrayList<>();

            for (Enrollment e : enrollments){
                User user = e.getUser();
                if (user.getRole().equalsIgnoreCase("student")){
                    students.add(user);
                }
            }
            return students;
        } else {
            return List.of();
        }
    }

    @GetMapping("/{id}/announcements")
    public List<Announcement> getAnnouncementsByCourse(@PathVariable Long id){
        Optional<Course> courseOpt = courseService.getCourseById(id);

        if (courseOpt.isPresent()){
            Course course = courseOpt.get();
            return course.getAnnouncements();
        } else {
            return List.of();
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<String> changeStatus(@PathVariable Long id){
        try {
            courseService.updateStatus(id);
            return ResponseEntity.ok("Changed status successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest()
                    .body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error handling change status: " + e.getMessage());
        }
    }
    @GetMapping("/{id}/assignments")
    public List<Activity> getAssignmentsByCourse(@PathVariable Long id){
        Optional<Course> courseOptional  =  courseService.getCourseById(id);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();

            List<Activity> activities= course.getActivities();

            return activities.stream()
                    .filter(activity -> activity.getActivityType().equalsIgnoreCase("student"))
                    .toList();
        } else {
            return List.of();
        }
    }

    @GetMapping("/{id}/peopl")
    public List<User> getAllPeopleByCourse(@PathVariable Long id){
            return courseService.getAllUserByCourse(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        try {
            courseService.addCourse(course);
            return ResponseEntity.ok("Course added successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid data: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the course.");
        }
    }

    @GetMapping("/{id}/published")
    public List<LearningMaterial> publishedModules(@PathVariable Long id){
        Optional<Course> courseOptional = courseService.getCourseById(id);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();

            List<LearningMaterial> learningMaterials = course.getLearningMaterials();

            return learningMaterials.stream().filter(LearningMaterial::isStatus)
                    .toList();
        } else {
            return List.of();
        }
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<?> getAllActivitiesByCourseId(@PathVariable Long id) {
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            Map<String, Object> activities = new HashMap<>();
            activities.put("announcements", course.getAnnouncements());
            activities.put("assignments", course.getActivities());
            activities.put("learningMaterials", course.getLearningMaterials());
            return ResponseEntity.ok(activities);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }


    @DeleteMapping("/delete")
    public void deleteCourseById(@RequestParam Long id){
        courseService.deleteCourseById(id);
    }
}
