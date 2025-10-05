package io.github.v4vinamra.algoforce.entities.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ContestDto {

    private Long id;
    private String event;
    private String href;
    private int duration;
    private LocalDateTime start;
    private String host;

}
