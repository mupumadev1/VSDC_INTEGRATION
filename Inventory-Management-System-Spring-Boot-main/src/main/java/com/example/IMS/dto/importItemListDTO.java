package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class importItemListDTO {
    private String taskCd;
    private String dclDe;
    private int itemSeq;
    private String dclNo;
    private String hsCd;
    private String itemNm;
    private String dclRefNum;
    private String itemCd;
    private String imptItemsttsCd;
    private String orgnNatCd;
    private String exptNatCd;
    private int pkg;
    private String pkgUnitCd;
    private int qty;
    private String qtyUnitCd;
    private double totWt;
    private double netWt;
    private String spplrNm;
    private String agntNm;
    private double invcFcurAmt;
    private String invcFcurCd;
    private double invcFcurExcrt;
}
