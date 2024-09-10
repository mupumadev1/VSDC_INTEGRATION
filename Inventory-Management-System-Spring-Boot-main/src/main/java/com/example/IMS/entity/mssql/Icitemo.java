package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ICITEMO", schema = "dbo")
public class Icitemo {
    @EmbeddedId
    private IcitemoId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "\"VALUE\"", nullable = false, length = 60)
    private String value;

    @Column(name = "TYPE", nullable = false)
    private Short type;

    @Column(name = "LENGTH", nullable = false)
    private Short length;

    @Column(name = "DECIMALS", nullable = false)
    private Short decimals;

    @Column(name = "ALLOWNULL", nullable = false)
    private Short allownull;

    @Column(name = "VALIDATE", nullable = false)
    private Short validate;

    @Column(name = "SWSET", nullable = false)
    private Short swset;

}