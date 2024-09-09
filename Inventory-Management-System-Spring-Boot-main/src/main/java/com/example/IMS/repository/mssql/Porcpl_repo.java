package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcpl;
import com.example.IMS.entity.mssql.PorcplId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface Porcpl_repo extends JpaRepository<Porcpl, PorcplId> {

    @Query(value = "SELECT COALESCE(MAX(p.rcplseq), 0) + 2 FROM Porcpl p")
    BigDecimal findByRcphseq();
    @Query("SELECT COALESCE(MAX(p.rcplseq), 0) + 2 FROM Porcpl p")
    BigDecimal findMaxRcplseqPlusTwo();


}
