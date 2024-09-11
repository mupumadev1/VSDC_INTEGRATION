package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.App02;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface App02Repository extends JpaRepository<App02, String> {
    @Query("SELECT invcbtch FROM App02 where recid02 = 'APP02'")
    Integer findbatchid();
    Optional<App02> findByRecid02(String recid02);

}