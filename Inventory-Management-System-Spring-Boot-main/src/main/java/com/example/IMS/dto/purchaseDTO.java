package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class purchaseDTO {
    private String tpin;
    private String bhfId;
    private String cisInvcNo;
    private int orgInvcNo;
    private String spplrTpin;
    private String spplrBhfId;
    private String spplrNm;
    private String spplrInvcNo;
    private String regTyCd;
    private String pchsTyCd;
    private String rcptTyCd;
    private String pmtTyCd;
    private String pchsSttsCd;
    private String cfmDt;
    private String pchsDt;
    private String wrhsDt;
    private String cnclReqDt;
    private String cnclDt;
    private String rfdDt;
    private int totItemCnt;
    private double totTaxblAmt;
    private double totTaxAmt;
    private double totAmt;
    private String remark;
    private String regrNm;
    private String regrId;
    private String modrNm;
    private String modrId;
    private List<purchaseItemDTO> itemList;
}
