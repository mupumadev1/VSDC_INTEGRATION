package com.example.IMS.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class selectPurchasesItemsDTO {
    private int itemSeq;
    private String itemCd;
    private String itemClsCd;
    private String itemNm;
    private String bcd;
    private String pkgUnitCd;
    private int pkg;
    private String qtyUnitCd;
    private int qty;
    private BigDecimal prc;
    private BigDecimal splyAmt;
    private BigDecimal dcRt;
    private BigDecimal dcAmt;
    private String vatCatCd;
    private String iplCatCd;
    private String tlCatCd;
    private String exciseTxCatCd;
    private BigDecimal vatTaxblAmt;
    private BigDecimal exciseTaxblAmt;
    private BigDecimal iplTaxblAmt;
    private BigDecimal tlTaxblAmt;
    private BigDecimal taxblAmt;
    private BigDecimal vatAmt;
    private BigDecimal iplAmt;
    private BigDecimal tlAmt;
    private BigDecimal exciseTxAmt;
    private BigDecimal totAmt;
}
