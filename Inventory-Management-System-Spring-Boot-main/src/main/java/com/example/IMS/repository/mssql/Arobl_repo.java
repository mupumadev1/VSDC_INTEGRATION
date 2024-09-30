package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Apobl;
import com.example.IMS.entity.mssql.Arobl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface Arobl_repo extends JpaRepository<Arobl, Integer> {
    @Query(value = "SELECT * FROM Arobl WHERE AUDTDATE = :todaysDate AND TRXTYPETXT IN (1, 2, 3) AND AROBL.IDINVC NOT IN (:docObjects)", nativeQuery = true)
    List<Arobl> findByDocIds(@Param("todaysDate") BigDecimal todaysDate, @Param("docObjects") List<String> docObjects);

    @Query(value = "SELECT * FROM Arobl WHERE AUDTDATE = :todaysDate AND AROBL.TRXTYPETXT IN (1, 2, 3)",nativeQuery = true)
    List<Arobl> findByAudtdate(@Param("todaysDate")  BigDecimal todaysDate);

    @Query(value = "SELECT * FROM Arobl WHERE IDINVC = :invc",nativeQuery = true)
   Arobl findByIdinvc( String invc);

    @Query(value = "SELECT * FROM Arobl WHERE audtdate BETWEEN :startDate AND :endDate AND idinvc NOT IN (:documentIds)", nativeQuery = true)
    List<Arobl> findByAudtdateBetween(@Param("startDate") BigDecimal startDate, @Param("endDate") BigDecimal endDate, @Param("documentIds") List<String> documentIds);

}
