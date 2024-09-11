package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apibc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ApibcRepository extends JpaRepository<Apibc, BigDecimal> {
}