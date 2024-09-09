package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcpal;
import com.example.IMS.entity.mssql.PorcpalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface PorcpalRepository extends JpaRepository<Porcpal, PorcpalId> {
    @Query("SELECT COALESCE(MAX(p.id.rcpalseq), 0) FROM Porcpal p")
    BigDecimal findByRcpal();

}