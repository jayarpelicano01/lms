////package proj.ws101.lms.controller;
////
////import ch.qos.logback.core.pattern.parser.OptionTokenizer;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////import proj.ws101.lms.entity.LearningMaterial;
////import proj.ws101.lms.service.LearningMaterialService;
////
////import java.util.List;
////import java.util.Optional;
////import java.util.OptionalInt;
////
////@RestController
////@RequestMapping("/modules")
////public class LearningMaterialController {
////
////    private final LearningMaterialService  learningMaterialService;
////
////    public LearningMaterialController(LearningMaterialService learningMaterialService){
////        this.learningMaterialService =learningMaterialService;
////    }
////
////    @GetMapping
////    public List<LearningMaterial> getAllModules(){
////        return learningMaterialService.getAllModules();
////    }
////
////    @GetMapping("/{id}")
////    public Optional<LearningMaterial> getModuleById(Long id){
////        return learningMaterialService.getModuleById(id);
////    }
////
////    @PostMapping("/add")
////    public ResponseEntity<String> addModule(@RequestBody LearningMaterial learningMaterial){
////        try {
////            learningMaterialService.addModule(learningMaterial);
////            return ResponseEntity.ok("Module added successfully");
////        } catch (IllegalArgumentException e){
////            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
////        } catch (Exception e){
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("Error occured while adding module");
////        }
////    }
////
////    @DeleteMapping("/delete/{id}")
////    public ResponseEntity<String> deleteModule (@PathVariable Long id){
////        Optional<LearningMaterial> learningMaterialOptional = learningMaterialService.getModuleById(id);
////
////        if (learningMaterialOptional.isPresent()){
////            learningMaterialService.deleteModule(id);
////            return ResponseEntity.ok("Module deleted successfully");
////        } else {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
////        }
////    }
////}
//
//
//
//package proj.ws101.lms.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import proj.ws101.lms.entity.LearningMaterial;
//import proj.ws101.lms.service.LearningMaterialService;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/modules")
//public class LearningMaterialController {
//
//    private final LearningMaterialService learningMaterialService;
//
//    public LearningMaterialController(LearningMaterialService learningMaterialService) {
//        this.learningMaterialService = learningMaterialService;
//    }
//
//    @GetMapping
//    public List<LearningMaterial> getAllModules() {
//        return learningMaterialService.getAllModules();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<LearningMaterial> getModuleById(@PathVariable Long id) {
//        return learningMaterialService.getModuleById(id);
//    }
//
////    @PostMapping("/add")
////    public ResponseEntity<String> addModule(@RequestBody LearningMaterial learningMaterial) {
////        try {
////            learningMaterialService.addModule(learningMaterial);
////            return ResponseEntity.ok("Module added successfully");
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding module.");
////        }
////    }
//
//    @PostMapping(value = "/add", consumes = "multipart/form-data")
//    public ResponseEntity<String> addModule(
//            @RequestParam("moduleTitle") String moduleTitle,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("status") boolean status) {
//        try {
//            LearningMaterial learningMaterial = new LearningMaterial();
//            learningMaterial.setModuleTitle(moduleTitle);
//            learningMaterial.setModuleFile(file.getBytes());
//            learningMaterial.setStatus(status);
//            learningMaterialService.addModule(learningMaterial);
//            return ResponseEntity.ok("Module added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error occurred while adding module: " + e.getMessage());
//        }
//    }
//
//    @PatchMapping("/{id}/status")
//    public ResponseEntity<String> updateModuleStatus(@PathVariable Long id, @RequestParam boolean status) {
//        try {
//            learningMaterialService.updateModuleStatus(id, status);
//            return ResponseEntity.ok(status ? "Module published successfully." : "Module unpublished successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating status.");
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
//        Optional<LearningMaterial> moduleOptional = learningMaterialService.getModuleById(id);
//        if (moduleOptional.isPresent()) {
//            learningMaterialService.deleteModule(id);
//            return ResponseEntity.ok("Module deleted successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
//        }
//    }
//}
//



package proj.ws101.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proj.ws101.lms.entity.Course;
import proj.ws101.lms.entity.LearningMaterial;
import proj.ws101.lms.service.CourseService;
import proj.ws101.lms.service.LearningMaterialService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modules")
public class LearningMaterialController {

    private final LearningMaterialService learningMaterialService;
    private final CourseService courseService;

    public LearningMaterialController(LearningMaterialService learningMaterialService, CourseService courseService) {
        this.learningMaterialService = learningMaterialService;
        this.courseService = courseService;
    }

    // Get all modules
    @GetMapping
    public List<LearningMaterial> getAllModules() {
        return learningMaterialService.getAllModules();
    }

    // Get module by ID
    @GetMapping("/{id}")
    public Optional<LearningMaterial> getModuleById(@PathVariable Long id) {
        return learningMaterialService.getModuleById(id);
    }


//    // Add a new module with file upload
//    @PostMapping(value = "/add", consumes = "multipart/form-data")
//    public ResponseEntity<String> addModule(
//            @RequestParam("moduleTitle") String moduleTitle,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("status") boolean status) {
//        try {
//            if (file.isEmpty()) {
//                return ResponseEntity.badRequest().body("File is empty.");
//            }
//
//            LearningMaterial learningMaterial = new LearningMaterial();
//            learningMaterial.setModuleTitle(moduleTitle);
//            learningMaterial.setModuleFile(file.getBytes()); // Save file as byte array
//            learningMaterial.setStatus(status);
//            learningMaterialService.addModule(learningMaterial);
//
//            return ResponseEntity.ok("Module added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error occurred while adding module: " + e.getMessage());
//        }
//    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<String> addModule(
            @RequestParam("moduleTitle") String moduleTitle,
            @RequestParam("file") MultipartFile file,
            @RequestParam("status") boolean status,
            @RequestParam("courseId") Long courseId) {  // Accept courseId

        try {
            // Fetch the Course object using the courseId
            Optional<Course> course = courseService.getCourseById(courseId); // Assuming you have a Course service

            if (!course.isPresent()) {
                return ResponseEntity.badRequest().body("Invalid course ID.");
            }

            // Log the input parameters
            System.out.println("Received moduleTitle: " + moduleTitle);
            System.out.println("Received status: " + status);
            System.out.println("Received courseId: " + courseId);

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            LearningMaterial learningMaterial = new LearningMaterial();
            learningMaterial.setModuleTitle(moduleTitle);
            learningMaterial.setModuleFile(file.getBytes());
            learningMaterial.setStatus(status);
            learningMaterial.setCourse(course.get());  // Set the course for the LearningMaterial

            learningMaterialService.addModule(learningMaterial);

            return ResponseEntity.ok("Module added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding module: " + e.getMessage());
        }
    }


    // Update module status (publish/unpublish)
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateModuleStatus(@PathVariable Long id, @RequestParam boolean status) {
        try {
            learningMaterialService.updateModuleStatus(id, status);
            return ResponseEntity.ok(status ? "Module published successfully." : "Module unpublished successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating status.");
        }
    }

    // Delete a module
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
        Optional<LearningMaterial> moduleOptional = learningMaterialService.getModuleById(id);
        if (moduleOptional.isPresent()) {
            learningMaterialService.deleteModule(id);
            return ResponseEntity.ok("Module deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
        }
    }
}
