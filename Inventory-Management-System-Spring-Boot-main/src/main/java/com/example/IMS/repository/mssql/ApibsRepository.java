package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.ApibId;
import com.example.IMS.entity.mssql.Apibs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApibsRepository extends JpaRepository<Apibs, ApibId> {
}