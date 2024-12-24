package proj.ws101.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.ws101.lms.entity.Teacher;
import proj.ws101.lms.entity.User;
import proj.ws101.lms.repository.TeacherRepository;
import proj.ws101.lms.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    public TeacherService(@Autowired TeacherRepository teacherRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public void addTeacher(Teacher teacher) {
        // Ensure the user exists and set it to the teacher
        Optional<User> userOpt = userRepository.findById(teacher.getUser().getUserId());
        if (userOpt.isPresent()) {
            teacher.setUser(userOpt.get());
            teacherRepository.save(teacher);
        }
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}

