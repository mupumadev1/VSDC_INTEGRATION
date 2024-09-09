package com.example.IMS.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class supplierPurchaseOrderDTO {
    private String vendorCode;
    private String vendorName;
    private String poNumber;
    private BigDecimal taxableAmount;
    private BigDecimal totalAmount;
    private BigDecimal tax;
    private String tpin;

    private List<supplierPurchaseOrderItems> items;

}
