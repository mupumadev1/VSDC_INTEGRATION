package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcpn;
import com.example.IMS.entity.mssql.PorcpnId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorcpnRepository extends JpaRepository<Porcpn, PorcpnId> {
}