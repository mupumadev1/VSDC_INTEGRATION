package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class itemInformationDTO {
 private String itemName;
 private String itemCode;
 private String itemClassCode;
 private String packagingUnit;
}
