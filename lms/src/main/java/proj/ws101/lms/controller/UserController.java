package proj.ws101.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import proj.ws101.lms.entity.Course;
import proj.ws101.lms.entity.Enrollment;
import proj.ws101.lms.entity.User;
import proj.ws101.lms.login.LoginRequest;
import proj.ws101.lms.service.CourseService;
import proj.ws101.lms.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/students")
    public List<User> getAllStudents(){
        return userService.getAllStudents();
    }

    @GetMapping("/teachers")
    public List<User> getAllTeachers(){
        return userService.getAllTeachers();
    }

    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/courses")
    public List<Course> getCourseByStudent(@PathVariable Long id){
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();

            List<Enrollment> enrollments = user.getEnrollments();
            List<Course> courses = new ArrayList<>();

            for (Enrollment enrollment : enrollments){
                courses.add(enrollment.getCourse());
            }
            return courses.stream()
                    .filter(course -> "published".equalsIgnoreCase(course.getStatus()))
                    .toList();
        } else {
            return List.of();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody Boolean isActive) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setIsActive(isActive);
            userService.updateUser(id, user);
            return ResponseEntity.ok("User status updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }



    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable Long id,@RequestBody User user){
        userService.updateUser(id, user);
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
            Optional<User> userPresence = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (userPresence.isPresent()){
            User user = userPresence.get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
