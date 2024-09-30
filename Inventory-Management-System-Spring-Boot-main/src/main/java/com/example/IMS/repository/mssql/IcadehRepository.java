package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Icadeh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IcadehRepository extends JpaRepository<Icadeh, Integer> {
    @Query(value = "select max(ADJENSEQ) from Icadeh ",nativeQuery = true)
    int findMaxId();
    Icadeh findByReferenceContaining(String ref);
}