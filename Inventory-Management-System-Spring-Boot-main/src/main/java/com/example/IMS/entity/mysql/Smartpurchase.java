package com.example.IMS.entity.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "smartpurchase", schema = "sage_service")
public class Smartpurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "spplrInvc")
    private Integer spplrInvc;

}