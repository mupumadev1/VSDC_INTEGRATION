package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class stockMasterDTO {
    private String tpin;
    private String bhfId;
    private String regrId;
    private String regrNm;
    private String modrNm;
    private String modrId;
    private List<stockMasterItemDTO> stockItemList;
}
