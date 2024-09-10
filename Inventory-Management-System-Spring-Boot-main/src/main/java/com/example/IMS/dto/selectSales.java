package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class selectSales {
    private String tpin;
    private String bhfId; //Branch ID
    private String lastReqDt; //Last request date
}
