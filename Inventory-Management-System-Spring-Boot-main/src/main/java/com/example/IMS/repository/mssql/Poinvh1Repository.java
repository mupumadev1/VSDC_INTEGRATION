package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poinvh1;
import com.example.IMS.entity.mssql.Porcph1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface Poinvh1Repository extends JpaRepository<Poinvh1, BigDecimal> {
    @Query("SELECT p FROM Poinvh1 p WHERE p.id = (SELECT MAX(p2.id) FROM Poinvh1 p2)")
    Poinvh1 findMaxId();

    Poinvh1 findByRcpnumber(String poNumber);
    Poinvh1 findByInvnumber(String poNumber);
}