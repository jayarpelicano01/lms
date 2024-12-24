package proj.ws101.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.ws101.lms.entity.Announcement;
import proj.ws101.lms.service.AnnouncementService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService){
        this.announcementService = announcementService;
    }

    @GetMapping
    public List<Announcement> getAllAnnouncements(){
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/{id}")
    public Optional<Announcement> getAnnouncementById(@PathVariable Long id){
        return announcementService.getAnnouncementById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAnnouncement(@RequestBody Announcement announcement){
        try {
            announcementService.addAnnouncement(announcement);
            return ResponseEntity.ok("Announcement added successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occired while adding announcement");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id){
        Optional<Announcement> announcementOpt = announcementService.getAnnouncementById(id);
        if (announcementOpt.isPresent()){
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.ok("Announcement deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Announcement not found");
        }
    }
}
