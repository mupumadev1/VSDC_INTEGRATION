package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apibd;
import com.example.IMS.entity.mssql.ApibdId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApibdRepository extends JpaRepository<Apibd, ApibdId> {
}