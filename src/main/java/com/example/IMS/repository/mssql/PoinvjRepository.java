package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poinvj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PoinvjRepository extends JpaRepository<Poinvj, BigDecimal> {
}