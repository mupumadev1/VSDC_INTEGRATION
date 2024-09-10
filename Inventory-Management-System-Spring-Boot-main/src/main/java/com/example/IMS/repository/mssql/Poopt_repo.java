package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poopt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Poopt_repo extends JpaRepository<Poopt,Integer> {
    Poopt findByRatetype(String rt);
}
