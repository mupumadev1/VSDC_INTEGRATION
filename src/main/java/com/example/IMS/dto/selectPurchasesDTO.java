package com.example.IMS.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class selectPurchasesDTO {
    private String spplrTpin;
    private String spplrNm;
    private String spplrBhfId;
    private int spplrInvcNo;
    private String rcptTyCd;
    private String pmtTyCd;
    private LocalDateTime cfmDt;
    private LocalDate salesDt;
    private LocalDate stockRlsDt;
    private int totItemCnt;
    private BigDecimal totTaxblAmt;
    private BigDecimal totTaxAmt;
    private BigDecimal totAmt;
    private String remark;
    private List<selectPurchasesItemsDTO> itemList;
}
