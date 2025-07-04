package io.github.v4vinamra.algoforce.repository;

import io.github.v4vinamra.algoforce.entities.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface ContestRepository extends JpaRepository<Contest, Long> {
    void deleteAll();
    boolean existsByContestId(Long contestId);
    List<Contest> findByStartAfterOrderByStartAsc(LocalDateTime time);
}
