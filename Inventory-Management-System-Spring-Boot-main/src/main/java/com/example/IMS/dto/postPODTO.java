package com.example.IMS.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class postPODTO {
        private int invnumber;
        private String po_number;
        private List<postPOItemsDTO> item_details;
}
