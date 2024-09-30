package com.example.IMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class DataDTO {
    @JsonProperty("clsList")
    private List<ClsDTO> clsList;

    // Getters and Setters
}
