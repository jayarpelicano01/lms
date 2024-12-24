package proj.ws101.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.ws101.lms.entity.Activity;
import proj.ws101.lms.entity.Course;
import proj.ws101.lms.service.ActivityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    public final ActivityService activityService;

    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @GetMapping
    public List<Activity> getAllActivities(){
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public Optional<Activity> getActivityById(@PathVariable Long id){
        return activityService.getActivityById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addActivity(@RequestBody Activity activity){
        try {
            activityService.addActivity(activity);
                return ResponseEntity.ok("Activity added successfully");
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Invalid data: " + ex.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while adding the activity.");
            }
    }

    @PostMapping("/{courseId}/add")
    public ResponseEntity<String> addActivity(@PathVariable Long courseId, @RequestBody Activity activity) {
        try {
            Course course = new Course();
            course.setCourseId(courseId); // Associate activity with course
            activity.setCourse(course);
            activityService.addActivity(activity);
            return ResponseEntity.ok("Activity added successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid data: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the activity.");
        }
    }

    @GetMapping("/{courseId}/quizzes")
    public List<Activity> getQuizzesCourseId(@PathVariable Long courseId) {
        return activityService.getQuizzesByCourseId(courseId);
    }

    // Endpoint to get assignments by course ID
    @GetMapping("/{courseId}/assignments")
    public List<Activity> getAssignmentsByCourseId(@PathVariable Long courseId) {
        return activityService.getAssignmentsByCourseId(courseId);
    }

    @DeleteMapping("/delete")
    public void deleteActivity(@RequestParam Long id ){
        activityService.deleteActivityById(id);
    }
}
