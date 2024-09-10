package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class selectImportDTO {
    private String tpin;
    private String bhfId; //Branch ID
    private String lastReqDt; //Last request date
}
