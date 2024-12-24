package proj.ws101.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.ws101.lms.entity.Announcement;
import proj.ws101.lms.repository.AnnouncementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    public AnnouncementRepository announcementRepository;

    public AnnouncementService(@Autowired AnnouncementRepository announcementRepository){
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> getAllAnnouncements(){
        return announcementRepository.findAll();
    }

    public Optional<Announcement> getAnnouncementById(Long id){
        return announcementRepository.findById(id);
    }

    public void addAnnouncement(Announcement announcement){
        announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(Long id ){
        announcementRepository.deleteById(id);
    }
}
