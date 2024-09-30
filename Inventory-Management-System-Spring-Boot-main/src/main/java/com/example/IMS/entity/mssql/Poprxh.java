package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "POPRXH")
public class Poprxh {
    @Id
    @Column(name = "PRORSEQ", nullable = false, precision = 19)
    private BigDecimal id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "TOTCBASE", nullable = false, precision = 19, scale = 3)
    private BigDecimal totcbase;

    @Column(name = "TOTQBASE", nullable = false, precision = 19, scale = 4)
    private BigDecimal totqbase;

    @Column(name = "TOTWBASE", nullable = false, precision = 19, scale = 4)
    private BigDecimal totwbase;

    @Column(name = "TOTCRTNS", nullable = false, precision = 19, scale = 3)
    private BigDecimal totcrtns;

    @Column(name = "TOTQRTNS", nullable = false, precision = 19, scale = 4)
    private BigDecimal totqrtns;

    @Column(name = "TOTWRTNS", nullable = false, precision = 19, scale = 4)
    private BigDecimal totwrtns;

    @Column(name = "PRVCBASE", nullable = false, precision = 19, scale = 3)
    private BigDecimal prvcbase;

    @Column(name = "PRVQBASE", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvqbase;

    @Column(name = "PRVWBASE", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvwbase;

    @Column(name = "PRVCRTNS", nullable = false, precision = 19, scale = 3)
    private BigDecimal prvcrtns;

    @Column(name = "PRVQRTNS", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvqrtns;

    @Column(name = "PRVWRTNS", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvwrtns;

    @Column(name = "\"REFERENCES\"", nullable = false, precision = 19)
    private BigDecimal references;

    @Column(name = "VERPRORATE", nullable = false)
    private Short verprorate;

    @Column(name = "TOTCBASEB", nullable = false, precision = 19, scale = 3)
    private BigDecimal totcbaseb;

    @Column(name = "TOTQBASEB", nullable = false, precision = 19, scale = 4)
    private BigDecimal totqbaseb;

    @Column(name = "TOTWBASEB", nullable = false, precision = 19, scale = 4)
    private BigDecimal totwbaseb;

    @Column(name = "TOTCRTNSB", nullable = false, precision = 19, scale = 3)
    private BigDecimal totcrtnsb;

    @Column(name = "TOTQRTNSB", nullable = false, precision = 19, scale = 4)
    private BigDecimal totqrtnsb;

    @Column(name = "TOTWRTNSB", nullable = false, precision = 19, scale = 4)
    private BigDecimal totwrtnsb;

    @Column(name = "PRVCBASEB", nullable = false, precision = 19, scale = 3)
    private BigDecimal prvcbaseb;

    @Column(name = "PRVQBASEB", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvqbaseb;

    @Column(name = "PRVWBASEB", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvwbaseb;

    @Column(name = "PRVCRTNSB", nullable = false, precision = 19, scale = 3)
    private BigDecimal prvcrtnsb;

    @Column(name = "PRVQRTNSB", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvqrtnsb;

    @Column(name = "PRVWRTNSB", nullable = false, precision = 19, scale = 4)
    private BigDecimal prvwrtnsb;

    @Column(name = "RTGBASE", nullable = false)
    private Short rtgbase;

}