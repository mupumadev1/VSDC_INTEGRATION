package com.example.IMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DtlDTO {
    @JsonProperty("cd")
    private String cd;

    @JsonProperty("cdNm")
    private String cdNm;

    @JsonProperty("userDfnCd1")
    private String userDfnCd1;

    // Getters and Setters
}
