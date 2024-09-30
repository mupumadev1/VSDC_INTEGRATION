package com.example.IMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public  class CodeResponseDTO {
    @JsonProperty("resultCd")
    private String resultCd;

    @JsonProperty("resultMsg")
    private String resultMsg;

    @JsonProperty("resultDt")
    private String resultDt;

    @JsonProperty("data")
    private DataDTO data;

    // Getters and Setters
}
