package com.example.IMS.repository.mysql;

import com.example.IMS.entity.mysql.Smartpurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartpurchaseRepository extends JpaRepository<Smartpurchase, Integer> {
    Smartpurchase findBySpplrInvc(int invc);
}