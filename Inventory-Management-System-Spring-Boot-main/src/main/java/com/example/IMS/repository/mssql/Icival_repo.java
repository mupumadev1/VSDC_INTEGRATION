package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Icival;
import com.example.IMS.entity.mssql.IcivalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface Icival_repo extends JpaRepository<Icival, IcivalId> {
    @Query(value = "select * from Icival where AUDTDATE =:date and Itemno in (:docIds)and TRANSTYPE in (1,4)",nativeQuery = true)
    List<Icival> findByItemnoIn(List<String> docIds,BigDecimal date);
    @Query(value = "SELECT * FROM Icival i " +
            "WHERE AUDTDATE = :date " +
            "AND TRANSTYPE IN (1, 4) " +
            "AND AUDTTIME = (SELECT MAX(AUDTTIME) FROM Icival WHERE AUDTDATE = :date AND TRANSTYPE IN (1, 4) AND ITEMNO = i.ITEMNO)",
            nativeQuery = true)
    List<Icival> findByAudtdate(@Param("date") BigDecimal date);
}