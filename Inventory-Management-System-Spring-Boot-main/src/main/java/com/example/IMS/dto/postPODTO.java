package com.example.IMS.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class postPODTO {
        private String supplierTpin;
        private String supplierName;
        private String invoiceNumber;
        private String poNumber;
        private String remark;
        private List<SageItemDTO> sageItems;;

        // Getters and Setters

}
