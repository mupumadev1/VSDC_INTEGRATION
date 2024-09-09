package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Icitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface Icitem_repo extends JpaRepository<Icitem,Integer> {

    @Query(value = "select * from Icitem where audtdate = :todaysDate and itemno not in (:savedItems)  ",nativeQuery = true)
    List<Icitem> findExcludingItemCodes(List<String> savedItems, BigDecimal todaysDate);

    List<Icitem> findByAudtdate(BigDecimal todaysDate);
  Icitem findByItemno(String itemno);
    Icitem findByFmtitemno(String itemno);
    Optional<Icitem> findByDescContainsIgnoreCase(String desc);
}
