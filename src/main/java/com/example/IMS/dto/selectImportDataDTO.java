package com.example.IMS.dto;

import lombok.Data;

@Data
public class selectImportDataDTO {
    private String tpin;
    private String bhfId;
    private String lastReqDt;
    private String dclRefNum;
}
