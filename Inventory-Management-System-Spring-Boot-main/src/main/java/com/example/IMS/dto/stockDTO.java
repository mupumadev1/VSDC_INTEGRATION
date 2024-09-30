package com.example.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class stockDTO {
    private String tpin;
    private String bhfId;
    private int sarNo;
    private int orgSarNo;
    private String regTyCd;
    private String custTpin;
    private String custNm;
    private String custBhfId;
    private String sarTyCd;
    private String ocrnDt;
    private int totItemCnt;
    private double totTaxblAmt;
    private double totTaxAmt;
    private double totAmt;
    private String remark;
    private String regrId;
    private String regrNm;
    private String modrNm;
    private String modrId;
    private List<stockItemDTO> itemList;
}
