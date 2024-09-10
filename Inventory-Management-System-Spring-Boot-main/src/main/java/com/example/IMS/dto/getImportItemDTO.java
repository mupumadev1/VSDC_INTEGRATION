package com.example.IMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class getImportItemDTO {
    @JsonProperty("taskCd")
    private String taskCd;

    @JsonProperty("dclDe")
    private String dclDe;

    @JsonProperty("itemSeq")
    private Integer itemSeq;

    @JsonProperty("dclNo")
    private String dclNo;

    @JsonProperty("hsCd")
    private String hsCd;

    @JsonProperty("itemNm")
    private String itemNm;

    @JsonProperty("imptItemsttsCd")
    private String imptItemsttsCd;

    @JsonProperty("orgnNatCd")
    private String orgnNatCd;

    @JsonProperty("exptNatCd")
    private String exptNatCd;

    @JsonProperty("pkg")
    private Integer pkg;

    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("qtyUnitCd")
    private String qtyUnitCd;

    @JsonProperty("totWt")
    private BigDecimal totWt;

    @JsonProperty("netWt")
    private BigDecimal netWt;

    @JsonProperty("spplrNm")
    private String spplrNm;

    @JsonProperty("agntNm")
    private String agntNm;

    @JsonProperty("invcFcurAmt")
    private BigDecimal invcFcurAmt;

    @JsonProperty("invcFcurCd")
    private String invcFcurCd;

    @JsonProperty("invcFcurExcrt")
    private BigDecimal invcFcurExcrt;
}
