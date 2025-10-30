package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Save or update user.
     */
    @Transactional
    public User save(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name must be specified");
        }
        return userRepository.save(user);
    }

    /**
     * Find a user by username.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find a user by ID.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get a user by ID directly (returns null if not found).
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Get all users.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Get all students (filtered by role "STUDENT").
     */
    public List<User> findAllStudents() {
        return userRepository.findAll().stream()
                .filter(u -> "STUDENT".equalsIgnoreCase(u.getRole()))
                .toList();
    }

    /**
     * Alias for consistency.
     */
    public List<User> getAllStudents() {
        return findAllStudents();
    }

    /**
     * Get a student by ID (returns null if not a student or not found).
     */
    public User getStudentById(Long studentId) {
        return userRepository.findById(studentId)
                .filter(u -> "STUDENT".equalsIgnoreCase(u.getRole()))
                .orElse(null);
    }

    /**
     * Get all faculty (filtered by role "FACULTY").
     */
    public List<User> getAllFaculty() {
        return userRepository.findAll().stream()
                .filter(u -> "FACULTY".equalsIgnoreCase(u.getRole()))
                .toList();
    }

    /**
     * Get users by a given role.
     */
    public List<User> getUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(u -> role.equalsIgnoreCase(u.getRole()))
                .toList();
    }

    /**
     * Delete a user by ID.
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Check if a user exists by ID.
     */
    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Count users by role.
     */
    public long countUsersByRole(String role) {
        return getUsersByRole(role).size();
    }

    /**
     * Count all users.
     */
    public long countAllUsers() {
        return userRepository.count();
    }
}
