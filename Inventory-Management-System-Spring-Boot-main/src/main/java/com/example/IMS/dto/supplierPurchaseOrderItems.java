package com.example.IMS.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class supplierPurchaseOrderItems {
    private String itemCode;
    private String itemName;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal taxableAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;

}
