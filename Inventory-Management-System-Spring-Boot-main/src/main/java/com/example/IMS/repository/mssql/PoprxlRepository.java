package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poprxl;
import com.example.IMS.entity.mssql.PoprxlId;
import com.example.IMS.entity.mssql.Porcph1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface PoprxlRepository extends JpaRepository<Poprxl, PoprxlId> {
    @Query("SELECT MAX(p.id.lineseq) FROM Poprxl p")
    BigDecimal findMaxId();
}