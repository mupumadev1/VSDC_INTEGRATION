package com.example.IMS.dto;

import lombok.Data;

@Data
public class selectItemDTO {
    private String tpin;
    private String itemCd;
    private String itemClsCd;
    private String itemTyCd;
    private String itemNm;
    private String itemStdNm; // Can be null
    private String orgnNatCd;
    private String pkgUnitCd;
    private String qtyUnitCd;
    private String vatCatCd; // Note: VAT Category Code appears twice in your JSON, ensure this is intentional
    private String btchNo; // Can be null
    private String regBhfId;
    private String bcd;
    private Double dftPrc; // Assuming price is a decimal value
    private String manufacturerTpin;
    private String manufacturerItemCd;
    private Double rrp; // Assuming RRP is a decimal value
    private String svcChargeYn; // Yes/No indicator
    private String rentalYn; // Yes/No indicator
    private String addInfo; // Can be null
    private Integer sftyQty; // Assuming safety quantity is an integer
    private String isrcAplcbYn; // Yes/No indicator
    private String ZRAModYn; // Yes/No indicator
    private String useYn; // Yes/No indicator

}
