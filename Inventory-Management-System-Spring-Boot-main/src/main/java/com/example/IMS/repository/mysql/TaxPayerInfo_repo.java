package com.example.IMS.repository.mysql;

import com.example.IMS.entity.mysql.TaxPayerInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxPayerInfo_repo extends JpaRepository<TaxPayerInformation,Integer> {
}
