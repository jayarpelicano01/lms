//package proj.ws101.lms.service;
//
//import org.springframework.boot.Banner;
//import org.springframework.stereotype.Service;
//import proj.ws101.lms.entity.LearningMaterial;
//import proj.ws101.lms.repository.LearningMaterialRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class LearningMaterialService {
//
//    private final LearningMaterialRepository learningMaterialRepository;
//
//    public LearningMaterialService(LearningMaterialRepository learningMaterialRepository){
//        this.learningMaterialRepository = learningMaterialRepository;
//    }
//
//    public List<LearningMaterial> getAllModules(){
//        return learningMaterialRepository.findAll();
//    }
//
//    public Optional<LearningMaterial> getModuleById(Long id ){
//        return learningMaterialRepository.findById(id);
//    }
//
//    public void addModule(LearningMaterial learningMaterial){
//        learningMaterialRepository.save(learningMaterial);
//    }
//
//    public void deleteModule(Long id){
//        learningMaterialRepository.deleteById(id);
//    }
//}


package proj.ws101.lms.service;

import org.springframework.stereotype.Service;
import proj.ws101.lms.entity.LearningMaterial;
import proj.ws101.lms.repository.LearningMaterialRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LearningMaterialService {

    private final LearningMaterialRepository learningMaterialRepository;

    public LearningMaterialService(LearningMaterialRepository learningMaterialRepository) {
        this.learningMaterialRepository = learningMaterialRepository;
    }

    public List<LearningMaterial> getAllModules() {
        return learningMaterialRepository.findAll();
    }

    public Optional<LearningMaterial> getModuleById(Long id) {
        return learningMaterialRepository.findById(id);
    }

    public void addModule(LearningMaterial learningMaterial) {
        learningMaterial.setUploadedAt(LocalDateTime.now());
        learningMaterialRepository.save(learningMaterial);
    }

    public void deleteModule(Long id) {
        learningMaterialRepository.deleteById(id);
    }

    public void updateModuleStatus(Long id, boolean status) {
        Optional<LearningMaterial> moduleOptional = learningMaterialRepository.findById(id);
        if (moduleOptional.isPresent()) {
            LearningMaterial module = moduleOptional.get();
            module.setStatus(status);
            learningMaterialRepository.save(module);
        }
    }
}
