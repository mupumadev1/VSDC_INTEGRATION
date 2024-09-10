package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poinvah;
import com.example.IMS.entity.mssql.PoinvahId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoinvahRepository extends JpaRepository<Poinvah, PoinvahId> {
}