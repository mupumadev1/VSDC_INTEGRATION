package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Icaded;
import com.example.IMS.entity.mssql.IcadedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IcadedRepository extends JpaRepository<Icaded, IcadedId> {
}