package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apobl;
import com.example.IMS.entity.mssql.ApoblId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApoblRepository extends JpaRepository<Apobl, ApoblId> {
}