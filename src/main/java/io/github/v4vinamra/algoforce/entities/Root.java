package io.github.v4vinamra.algoforce.entities;

import io.github.v4vinamra.algoforce.entities.dtos.ContestDto;
import lombok.Data;

import java.util.List;

@Data
public class Root {
    private List<ContestDto> objects;
}
