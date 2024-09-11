package com.example.IMS.dto.mysql;

import lombok.Data;

import java.time.Instant;

@Data
public class pendingPurchases {
    private String invoiceNumber;
    private String responseMessage;
    private Instant requestDate;
}
