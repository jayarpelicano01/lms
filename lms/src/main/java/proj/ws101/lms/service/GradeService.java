package proj.ws101.lms.service;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.ws101.lms.entity.Grade;
import proj.ws101.lms.repository.GradeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;

    public GradeService(@Autowired GradeRepository gradeRepository){
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrade(){
        return gradeRepository.findAll();
    }


}

