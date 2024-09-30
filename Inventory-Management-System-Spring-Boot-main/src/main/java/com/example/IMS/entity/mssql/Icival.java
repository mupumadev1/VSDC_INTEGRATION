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
@Table(name = "ICIVAL")
public class Icival {
    @EmbeddedId
    private IcivalId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "CATEGORY", nullable = false, length = 6)
    private String category;

    @Column(name = "DOCNUM", nullable = false, length = 22)
    private String docnum;

    @Column(name = "TRANSTYPE", nullable = false)
    private Short transtype;

    @Column(name = "UNIT", nullable = false, length = 10)
    private String unit;

    @Column(name = "QUANTITY", nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(name = "CONVERSION", nullable = false, precision = 19, scale = 6)
    private BigDecimal conversion;

    @Column(name = "TRANSCOST", nullable = false, precision = 19, scale = 3)
    private BigDecimal transcost;

    @Column(name = "STKQTY", nullable = false, precision = 19, scale = 4)
    private BigDecimal stkqty;

    @Column(name = "OPTAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal optamt;

    @Column(name = "APP", nullable = false, length = 2)
    private String app;

    @Column(name = "STOCKUNIT", nullable = false, length = 10)
    private String stockunit;

    @Column(name = "DEFPRICLST", nullable = false, length = 6)
    private String defpriclst;

    @Column(name = "TOTALCOST", nullable = false, precision = 19, scale = 3)
    private BigDecimal totalcost;

    @Column(name = "RECENTCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal recentcost;

    @Column(name = "COST1", nullable = false, precision = 19, scale = 6)
    private BigDecimal cost1;

    @Column(name = "COST2", nullable = false, precision = 19, scale = 6)
    private BigDecimal cost2;

    @Column(name = "LASTCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal lastcost;

    @Column(name = "STDCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal stdcost;

    @Column(name = "COSTUNIT", nullable = false, length = 10)
    private String costunit;

    @Column(name = "COSTCONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal costconv;

    @Column(name = "TOTALQTY", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalqty;

    @Column(name = "PRICELIST", nullable = false, length = 6)
    private String pricelist;

    @Column(name = "PRICEDECS", nullable = false)
    private Short pricedecs;

    @Column(name = "BASEPRICE", nullable = false, precision = 19, scale = 6)
    private BigDecimal baseprice;

    @Column(name = "BASEUNIT", nullable = false, length = 10)
    private String baseunit;

    @Column(name = "BASECONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal baseconv;

    @Column(name = "DETAILNUM", nullable = false)
    private Short detailnum;

    @Column(name = "COMPNUM", nullable = false)
    private Integer compnum;

    @Column(name = "DATEBUS", nullable = false, precision = 9)
    private BigDecimal datebus;

}