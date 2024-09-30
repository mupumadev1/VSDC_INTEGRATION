package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class purchaseItemDTO {
    private int itemSeq;
    private String itemCd;
    private String itemClsCd;
    private String itemNm;
    private String bcd;
    private String pkgUnitCd;
    private double pkg;
    private String qtyUnitCd;
    private double qty;
    private double prc;
    private double splyAmt;
    private double dcRt;
    private double dcAmt;
    private String taxTyCd;
    private String iplCatCd;
    private String tlCatCd;
    private String exciseCatCd;
    private double taxblAmt;
    private String vatCatCd;
    private double iplTaxblAmt;
    private double tlTaxblAmt;
    private double exciseTaxblAmt;
    private double taxAmt;
    private double iplAmt;
    private double tlAmt;
    private double exciseTxAmt;
    private double totAmt;
}
