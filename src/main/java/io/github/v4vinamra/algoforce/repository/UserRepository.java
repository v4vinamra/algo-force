package io.github.v4vinamra.algoforce.repository;

import io.github.v4vinamra.algoforce.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    @Transactional
    long deleteByEmail(String email);
}
