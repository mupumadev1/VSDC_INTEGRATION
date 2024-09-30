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
@Table(name = "ICADED")
public class Icaded {
    @EmbeddedId
    private IcadedId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "ITEMNO", nullable = false, length = 24)
    private String itemno;

    @Column(name = "ITEMDESC", nullable = false, length = 60)
    private String itemdesc;

    @Column(name = "LOCATION", nullable = false, length = 6)
    private String location;

    @Column(name = "TRANSTYPE", nullable = false)
    private Short transtype;

    @Column(name = "QUANTITY", nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(name = "UNIT", nullable = false, length = 10)
    private String unit;

    @Column(name = "CONVERSION", nullable = false, precision = 19, scale = 6)
    private BigDecimal conversion;

    @Column(name = "EXTCOST", nullable = false, precision = 19, scale = 3)
    private BigDecimal extcost;

    @Column(name = "COMMENTS", nullable = false, length = 250)
    private String comments;

    @Column(name = "WOFFACCT", nullable = false, length = 45)
    private String woffacct;

    @Column(name = "ORIGACCT", nullable = false, length = 45)
    private String origacct;

    @Column(name = "COSTMETHOD", nullable = false)
    private Short costmethod;

    @Column(name = "MDALLOCATE", nullable = false)
    private Short mdallocate;

    @Column(name = "RECEIPT", nullable = false, length = 22)
    private String receipt;

    @Column(name = "COSTDATE", nullable = false, precision = 9)
    private BigDecimal costdate;

    @Column(name = "COSTSEQNUM", nullable = false)
    private Integer costseqnum;

    @Column(name = "STOCKITEM", nullable = false)
    private Short stockitem;

    @Column(name = "MANITEMNO", nullable = false, length = 24)
    private String manitemno;

    @Column(name = "DETAILNUM", nullable = false)
    private Short detailnum;

    @Column(name = "PMCONTRACT", nullable = false, length = 16)
    private String pmcontract;

    @Column(name = "PMPROJECT", nullable = false, length = 16)
    private String pmproject;

    @Column(name = "PMCATEGORY", nullable = false, length = 16)
    private String pmcategory;

    @Column(name = "PMOHACCT", nullable = false, length = 45)
    private String pmohacct;

    @Column(name = "PMOHAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal pmohamt;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "SERIALQTY", nullable = false)
    private Integer serialqty;

    @Column(name = "LOTQTY", nullable = false, precision = 19, scale = 4)
    private BigDecimal lotqty;

    @Column(name = "SERIALCOST", nullable = false, precision = 19, scale = 3)
    private BigDecimal serialcost;

    @Column(name = "LOTCOST", nullable = false, precision = 19, scale = 3)
    private BigDecimal lotcost;

}