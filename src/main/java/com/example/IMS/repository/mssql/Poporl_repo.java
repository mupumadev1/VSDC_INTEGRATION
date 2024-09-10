package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poporl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface Poporl_repo extends JpaRepository<Poporl, Integer> {
    @Query(value = "SELECT * FROM Poporl WHERE PORHSEQ = :id",nativeQuery = true)
    List<Poporl> findById(BigDecimal id);

}
