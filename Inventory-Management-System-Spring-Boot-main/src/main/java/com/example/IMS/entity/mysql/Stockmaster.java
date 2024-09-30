package com.example.IMS.entity.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stockmaster", schema = "sage_service")
public class Stockmaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "itemCode", nullable = false, length = 22)
    private String itemCode;

    @Column(name = "itemQuantity", nullable = false)
    private Integer itemQuantity;

}