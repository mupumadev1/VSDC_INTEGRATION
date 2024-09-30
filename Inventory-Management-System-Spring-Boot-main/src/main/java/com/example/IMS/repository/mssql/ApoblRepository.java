package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apobl;
import com.example.IMS.entity.mssql.ApoblId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ApoblRepository extends JpaRepository<Apobl, ApoblId> {
    @Query(value = "SELECT * FROM Apobl WHERE idinvc = :invc ",nativeQuery = true)
    Apobl findByIdinvc( String invc);

    @Query(value = "SELECT * FROM Apobl WHERE audtdate BETWEEN :startDate AND :endDate AND idinvc NOT IN (:documentIds)", nativeQuery = true)
    List<Apobl> findByAudtdateBetween(@Param("startDate") BigDecimal startDate, @Param("endDate") BigDecimal endDate, @Param("documentIds") List<String> documentIds);
}