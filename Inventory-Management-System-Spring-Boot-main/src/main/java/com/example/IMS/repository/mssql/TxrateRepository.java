package com.example.IMS.repository.mssql;

import com.example.IMS.entity.mssql.Txrate;
import com.example.IMS.entity.mssql.TxrateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TxrateRepository extends JpaRepository<Txrate, TxrateId> {
    @Query(value = "select * from Txrate  where authority ='VATZMW      ' and buyerclass in (1,2,8)",nativeQuery = true)
    List<Txrate> findByTaxclass();
}