package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poinvn;
import com.example.IMS.entity.mssql.PoinvnId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoinvnRepository extends JpaRepository<Poinvn, PoinvnId> {
}