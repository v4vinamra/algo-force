package io.github.v4vinamra.algoforce.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/health")
public class HealthCheck {


    @GetMapping("/1")
    public ResponseEntity<?> updateUser(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
