package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Porcph1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Porcph1_repo extends JpaRepository<Porcph1,Integer> {

    @Query("SELECT p FROM Porcph1 p WHERE p.id = (SELECT MAX(p2.id) FROM Porcph1 p2)")
    Porcph1 findMaxId();
    @Query(value = "SELECT * FROM PORCPH1 WHERE iscomplete=1 AND isinvoiced=1 ",nativeQuery = true)
    List<Porcph1> findByComplete();
    List<Porcph1> findByVdcodeAndIscompleteAndIsinvoiced(String vendor, Short status,Short inv);
    Porcph1 findByRcpnumberContaining(String rcp);
    Porcph1 findByInvnumber(String inv);
}
