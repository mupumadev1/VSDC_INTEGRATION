package com.example.IMS.entity.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "auditlog", schema = "sage_service")
public class Auditlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "request_date", nullable = false)
    private Instant requestDate;

    @Column(name = "tpin", nullable = false, length = 10)
    private String tpin;

    @Column(name = "last_request_date", nullable = false, length = 14)
    private String lastRequestDate;

    @Column(name = "transaction_type", nullable = false, length = 14)
    private String transactionType;

    @Column(name = "response_code", nullable = false, length = 45)
    private String responseCode;

    @Column(name = "document_id", length = 100)
    private String documentId;

    @Column(name = "request_url", nullable = false, length = 100)
    private String requestUrl;


    @Column(name = "request_body" ,columnDefinition = "LONGTEXT", nullable = false)
    private String requestBody;

    @Lob
    @Column(name = "response_message" ,columnDefinition = "LONGTEXT", nullable = false)
    private String responseMessage;

    @ColumnDefault("'A'")
    @Column(name = "purchaseTransactionType", length = 1)
    private String purchaseTransactionType;

}