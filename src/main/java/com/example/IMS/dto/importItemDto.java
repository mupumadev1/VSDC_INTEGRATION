package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class importItemDto {
    private String resultCd;
    private String resultMsg;
    private String resultDt;
    private importDataDTO data;
}
