package com.example.IMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class importDataDTO {
    @JsonProperty("itemList")
    private List<importDataDTO> itemList;
}
