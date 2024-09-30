package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poporc;
import com.example.IMS.entity.mssql.PoporcId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoporcRepository extends JpaRepository<Poporc, PoporcId> {
}