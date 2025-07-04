package io.github.v4vinamra.algoforce.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contest")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private Long contestId;
    private String title;
    private String registrationUrl;
    private int duration;
    private LocalDateTime start;
    private String platformUrl;
    private String platform;

}