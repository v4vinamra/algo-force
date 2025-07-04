package io.github.v4vinamra.algoforce.controller;

import io.github.v4vinamra.algoforce.entities.Contest;
import io.github.v4vinamra.algoforce.entities.User;
import io.github.v4vinamra.algoforce.service.ContestFetchingService;
import io.github.v4vinamra.algoforce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final ContestFetchingService contestFetchingService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        userService.updateUserCredentials(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        boolean removed = userService.deleteByUsername();
        if (removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contests/upcoming")
    public List<Contest> fetchContest(){
        return contestFetchingService.getAllUpcomingContests();
    }

}
