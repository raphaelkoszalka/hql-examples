package com.philips.philips.controller;

import com.philips.philips.exception.ResourceNotFoundException;
import com.philips.philips.model.User;
import com.philips.philips.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getQuestions(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User createQuestion(@Valid @RequestBody User question) {
        return userRepository.save(question);
    }

    @PutMapping("/users/{userId}")
    public User updateQuestion(@PathVariable Long userId,@Valid @RequestBody User userRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setSurname(userRequest.getSurname());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(question -> {
                    userRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
