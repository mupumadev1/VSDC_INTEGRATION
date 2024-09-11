package com.example.IMS.entity.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "iteminformation", schema = "sage_service")
public class Iteminformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "itemCode", nullable = false, length = 100)
    private String itemCode;

    @Column(name = "itemClass", nullable = false, length = 10)
    private String itemClass;


    @Column(name = "itemName", nullable = false, length = 60)
    private String itemName;

    @Column(name = "package_unit", nullable = false, length = 30)
    private String packageUnit;

    @Column(name = "quantity_unit", nullable = false, length = 30)
    private String quantityUnit;

}