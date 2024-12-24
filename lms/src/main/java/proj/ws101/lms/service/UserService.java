package proj.ws101.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.ws101.lms.entity.Course;
import proj.ws101.lms.entity.User;
import proj.ws101.lms.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getAllStudents(){
        return userRepository.findByRole("student");
    }

    public Optional<User> loginUser(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public List<User> getAllTeachers(){
        return userRepository.findByRole("teacher");
    }

    public void addUser(User student){
        userRepository.save(student);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setMiddleName(user.getMiddleName());
            updatedUser.setSuffix(user.getSuffix());
            updatedUser.setRole(user.getRole());
            updatedUser.setUserProfile(user.getUserProfile());
            updatedUser.setIsActive(user.getIsActive());
            userRepository.save(updatedUser);
        }
    }
}

