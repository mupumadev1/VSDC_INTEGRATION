package com.example.IMS.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class importPostDTO {
    private String taskCd;
    private String dclDe;
    private String dclNo;
    private String hsCd;
    private String itemNm;
    private BigDecimal qty; // Assuming qty is a number, use BigDecimal for precision
    private BigDecimal totWt; // Assuming totWt is also a number
    private String spplrNm;
    private String orgnNatCd; // Hidden field
    private String exptNatCd; // Hidden field
    private String pkg; // Hidden field
    private String pkgUnitCd; // Hidden field
    private String qtyUnitCd; // Hidden field
    private BigDecimal invcFcurAmt; // Assuming invcFcurAmt is a number
    private BigDecimal invcFcurExcrt; // Assuming invcFcurAmt is a number
    private String dclRefNum; // Hidden field
    private String sageItem; // Selected value from dropdown
    private String recieptNo; // Selected value from dropdown
    private String remark; // Selected value from dropdown
    private int itemSeq; // Selected value from dropdown
    private int actionValue; // Button value (3 for Confirm, 4 for Reject)

}
