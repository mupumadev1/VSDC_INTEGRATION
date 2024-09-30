package com.example.IMS.dto;

import lombok.Data;

import java.util.List;
@Data
public class postImportDTO {
    private String tpin;
    private String bhfId;
    private String taskCd;
    private String dclDe;
    private List<postImportItemDTO> importItemList;

    // Getters and Setters

}
