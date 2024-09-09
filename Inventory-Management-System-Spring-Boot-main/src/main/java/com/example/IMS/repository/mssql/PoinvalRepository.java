package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poinval;
import com.example.IMS.entity.mssql.PoinvalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoinvalRepository extends JpaRepository<Poinval, PoinvalId> {
}