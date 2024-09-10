package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcpj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface PorcpjRepository extends JpaRepository<Porcpj, BigDecimal> {
    @Query("SELECT MAX(p.prorateseq) FROM Porcpj p")
    BigDecimal findMaxProrateSeq();
}