package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Poporh1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Poporh1_repo extends JpaRepository<Poporh1, Integer> {
    List<Poporh1> findByVdcodeAndIscomplete(String vdcode,short status);
    Poporh1 findByPonumberContaining(String po);
    @Query(value = "SELECT * from poporh1 where iscomplete=0 and AUDTDATE > 20240101 ORDER BY AUDTDATE DESC",nativeQuery = true)
    List<Poporh1> findIncomplete();

}
