package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Csoptfd;
import com.example.IMS.entity.mssql.CsoptfdId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CsoptfdRepository extends JpaRepository<Csoptfd, CsoptfdId> {
    @Query(value = "SELECT * FROM csoptfd WHERE OPTFIELD LIKE CONCAT('%', :opt, '%')", nativeQuery = true)
    List<Csoptfd> findByOptfield(String opt);
}