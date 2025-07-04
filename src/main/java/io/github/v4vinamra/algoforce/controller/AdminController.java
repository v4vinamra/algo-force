package io.github.v4vinamra.algoforce.controller;


import io.github.v4vinamra.algoforce.entities.User;
import io.github.v4vinamra.algoforce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<String> createAdmin(@RequestBody User user) {
        userService.saveNewAdmin(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");
    }

}
