package com.example.IMS.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class codeDTO {
    private String tpin;
    private String bhfId; //Branch ID
    private String lastReqDt; //Last request date
}
