package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Icitemo;
import com.example.IMS.entity.mssql.IcitemoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Icitemo_repo extends JpaRepository<Icitemo, IcitemoId> {
    @Query("SELECT i FROM Icitemo i WHERE i.id.itemno = :itemno")
    List<Icitemo> findByItemno(@Param("itemno") String itemno);
}
