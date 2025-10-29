package com.JavaProject.Javasem.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User u) {
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    

    public List<User> findAllStudents() {
        return userRepository.findAll().stream()
                .filter(u -> "STUDENT".equalsIgnoreCase(u.getRole()))
                .toList();
    }

    public List<User> getAllStudents() {
        return userRepository.findAll();
    }

    public User getUserById(Long studentId) {
        return null;
    }

    public Object getAllFaculty() {
        return null;
    }
}
