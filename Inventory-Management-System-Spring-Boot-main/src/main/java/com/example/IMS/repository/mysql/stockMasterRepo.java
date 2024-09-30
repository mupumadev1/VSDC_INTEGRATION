package com.example.IMS.repository.mysql;

import com.example.IMS.entity.mysql.Stockmaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface stockMasterRepo extends JpaRepository<Stockmaster,Integer> {
}
