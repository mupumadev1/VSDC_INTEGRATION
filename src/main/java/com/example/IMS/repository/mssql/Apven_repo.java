package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apven;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Apven_repo extends JpaRepository<Apven, Integer> {


    Optional<Apven> findByVendorid(String vdcode);
    Optional<Apven> findByBrnAndVendname(String brn,String name);
}
