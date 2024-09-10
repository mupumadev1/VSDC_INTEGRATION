package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcpah;
import com.example.IMS.entity.mssql.PorcpahId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorcpahRepository extends JpaRepository<Porcpah, PorcpahId> {
}