package io.github.v4vinamra.algoforce.controller;

import io.github.v4vinamra.algoforce.entities.Contest;
import io.github.v4vinamra.algoforce.entities.User;
import io.github.v4vinamra.algoforce.scheduler.ContestFetchingScheduler;
import io.github.v4vinamra.algoforce.service.ContestFetchingService;
import io.github.v4vinamra.algoforce.service.UserDetailsServiceImpl;
import io.github.v4vinamra.algoforce.service.UserService;
import io.github.v4vinamra.algoforce.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final UserService userService;
    private final ContestFetchingScheduler contestScheduler;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody User user){
        userService.saveNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(Map.of("token", jwt));
        }
        catch (Exception e){
            log.error("Login failed for email {}: {}", user.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Incorrect email or password"));
        }
    }


    @PostMapping("/cf")
    public void fetch(){
        contestScheduler.fetchContest();
    }

}
