package com.example.IMS.entity.mysql;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TaxPayerInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String tpin;

    @Column(length = 60, nullable = false)
    private String taxPayerName;

    @Column(length = 100)
    private String businessActivity;

    @Column(length = 3, nullable = false)
    private String branchId;

    @Column(length = 100)
    private String branchName;

    @Column(length = 8)
    private String branchDateCreated;

    @Column(length = 100)
    private String province;

    @Column(length = 100)
    private String district;

    @Column(length = 100)
    private String sector;

    @Column(length = 100)
    private String location;

    @Column(length = 1)
    private String hq;

    @Column(length = 100)
    private String managerName;

    @Column(length = 100)
    private String managerNumber;

    @Column(length = 100)
    private String managerEmail;

    @Column(length = 100)
    private String deviceid;

    @Column(length = 100)
    private String sdcid;

    @Column(length = 100)
    private String mrcid;

    @Column(length = 255)
    private String internalKey;

    @Column(length = 255)
    private String signKey;

    @Column(length = 38)
    private String lastSaleInvoiceNumber;

    @Column(length = 38)
    private String lastPurchaseInvoiceNumber;

    @Column(length = 38)
    private String lastSaleReceiptNumber;

    @Column(length = 38)
    private String lastInvoiceNumber;

    @Column(length = 38)
    private String lastProformaInvoiceNumber;

    @Column(length = 38)
    private String lastCopyInvoiceNumber;
}
