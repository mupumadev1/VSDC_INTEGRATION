package com.example.IMS.repository.mysql;

import com.example.IMS.entity.mysql.Iteminformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IteminformationRepository extends JpaRepository<Iteminformation, Integer> {
}