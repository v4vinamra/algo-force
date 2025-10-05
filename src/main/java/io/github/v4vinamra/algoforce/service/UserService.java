package io.github.v4vinamra.algoforce.service;

import io.github.v4vinamra.algoforce.entities.User;
import io.github.v4vinamra.algoforce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // create user
    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }

    //update user
    public void updateUserCredentials(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email from JWT

        Optional<User> userInDbOptional = userRepository.findByEmail(email);
        if (userInDbOptional.isPresent()) {
            User userInDb = userInDbOptional.get();
            if (user.getUsername() != null && !user.getUsername().isBlank()) {
                Optional<User> userWithSameUsername = userRepository.findByUsername(user.getUsername());
                if (userWithSameUsername.isPresent() &&
                        !userWithSameUsername.get().getEmail().equals(userInDb.getEmail())) {
                    throw new IllegalArgumentException("Username already taken");
                }
                userInDb.setUsername(user.getUsername());
            }
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(userInDb);
        }
    }


    // delete user
    public boolean deleteByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.deleteByEmail(email) > 0;
    }

    // fetch users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // create new admin
    public void saveNewAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
    }
}
