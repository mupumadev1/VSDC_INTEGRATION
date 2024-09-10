package com.example.IMS.controller;




import com.example.IMS.dto.selectPurchasesDTO;
import com.example.IMS.dto.supplierPurchaseOrderDTO;

import java.util.List;

public class PurchasesResponse {
    private List<selectPurchasesDTO> purchases;
    private List<supplierPurchaseOrderDTO> additionalData; // Replace AnotherDTO with your actual DTO class

    public PurchasesResponse(List<selectPurchasesDTO> purchases, List<supplierPurchaseOrderDTO> additionalData) {
        this.purchases = purchases;
        this.additionalData = additionalData;
    }

    public List<selectPurchasesDTO> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<selectPurchasesDTO> purchases) {
        this.purchases = purchases;
    }

    public List<supplierPurchaseOrderDTO> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(List<supplierPurchaseOrderDTO> additionalData) {
        this.additionalData = additionalData;
    }
}
