package com.example.IMS.repository.mysql;

import com.example.IMS.entity.mysql.Auditlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuditlogRepository extends JpaRepository<Auditlog, Long> {
    @Query(value = "select document_id from `sage_service`.auditlog where transaction_type = :transactionType and response_code != '000' ",nativeQuery = true)
    List<Auditlog> findDocumentIdsByTransactionTypeAndResponseCode(String transactionType);
}