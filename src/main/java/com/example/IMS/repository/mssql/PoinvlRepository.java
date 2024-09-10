package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poinvl;
import com.example.IMS.entity.mssql.PoinvlId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface PoinvlRepository extends JpaRepository<Poinvl, PoinvlId> {
    @Query(value = "SELECT COALESCE(MAX(p.rcplseq), 0) + 2 FROM Poinvl p")
    BigDecimal findByRcphseq();
}