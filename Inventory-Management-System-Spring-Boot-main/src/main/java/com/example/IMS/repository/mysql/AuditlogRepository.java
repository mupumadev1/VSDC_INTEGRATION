package com.example.IMS.repository.mysql;

import com.example.IMS.entity.mysql.Auditlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuditlogRepository extends JpaRepository<Auditlog, Long> {
    @Query(value = "SELECT * FROM `sage_service`.auditlog a " +
            "WHERE a.transaction_type = :transactionType " +
            "AND a.response_code != '000' " +
            "AND NOT EXISTS (SELECT 1 FROM `sage_service`.auditlog b " +
            "WHERE b.document_id = a.document_id AND b.response_code = '000')",
            nativeQuery = true)
    List<Auditlog> findDocumentIdsByTransactionTypeAndResponseCode(String transactionType);
    @Query(value = "SELECT * FROM `sage_service`.auditlog ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Auditlog findLastRecord();
    @Query(value = "SELECT document_id FROM `sage_service`.auditlog a " +
            "WHERE a.transaction_type = :transactionType " +
            "AND a.response_code != '000' " +
            "AND NOT EXISTS (SELECT 1 FROM `sage_service`.auditlog b " +
            "WHERE b.document_id = a.document_id AND b.response_code = '000')",
            nativeQuery = true)
    List<String> findDocumentIds(String transactionType);
    @Query(value = "select document_id from `sage_service`.auditlog where transaction_type = :transactionType and response_code = '000' ",nativeQuery = true)
    List<String> findDocumentIdsByTransactionType(String transactionType);

    @Query(value = "select * from sage_service.auditlog where response_code = '902'  LIMIT 1 ",nativeQuery = true)
    Optional<Auditlog> findInstalledDevice();
    Auditlog findByDocumentId(String doc);
}