package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcph1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Porcph1_repo extends JpaRepository<Porcph1,Integer> {

    @Query("SELECT p FROM Porcph1 p WHERE p.id = (SELECT MAX(p2.id) FROM Porcph1 p2)")
    Porcph1 findMaxId();
}
