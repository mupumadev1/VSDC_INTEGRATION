package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poprxh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface PoprxhRepository extends JpaRepository<Poprxh, BigDecimal> {
    @Query(value = "SELECT MAX (p.prorseq) FROM Poprxh p ",nativeQuery = true)
    BigDecimal findMaxId();
}