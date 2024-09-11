package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apibh;
import com.example.IMS.entity.mssql.ApibhId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApibhRepository extends JpaRepository<Apibh, ApibhId> {
}