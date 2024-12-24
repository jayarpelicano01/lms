package proj.ws101.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.ws101.lms.entity.Enrollment;
import proj.ws101.lms.service.EnrollmentService;

@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> enroll(@RequestBody Enrollment enrollment) {
        try {
            enrollmentService.enroll(enrollment);
            return ResponseEntity.ok("Enrolled Successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace to the logs for more details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while enrolling.");
        }
    }

    @DeleteMapping("/drop")
    public ResponseEntity<String> dropEnrollment(@RequestParam Long id){
        try{
            enrollmentService.dropEnrollment(id);
            return ResponseEntity.ok("Enrollment dropped successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while dropping enrollment.");
        }
    }
}
