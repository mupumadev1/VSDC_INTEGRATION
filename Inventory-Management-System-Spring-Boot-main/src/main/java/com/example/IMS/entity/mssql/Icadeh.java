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
@Table(name = "ICADEH")
public class Icadeh {
    @Id
    @Column(name = "ADJENSEQ", nullable = false)
    private Integer id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "TRANSNUM", nullable = false, precision = 19)
    private BigDecimal transnum;

    @Column(name = "DOCNUM", nullable = false, length = 22)
    private String docnum;

    @Column(name = "HDRDESC", nullable = false, length = 60)
    private String hdrdesc;

    @Column(name = "TRANSDATE", nullable = false, precision = 9)
    private BigDecimal transdate;

    @Column(name = "FISCYEAR", nullable = false, length = 4)
    private String fiscyear;

    @Column(name = "FISCPERIOD", nullable = false)
    private Short fiscperiod;

    @Column(name = "REFERENCE", nullable = false, length = 60)
    private String reference;

    @Column(name = "DOCUNIQ", nullable = false, precision = 19)
    private BigDecimal docuniq;

    @Column(name = "STATUS", nullable = false)
    private Short status;

    @Column(name = "DELETED", nullable = false)
    private Short deleted;

    @Column(name = "NEXTDTLNUM", nullable = false)
    private Short nextdtlnum;

    @Column(name = "PRINTED", nullable = false)
    private Short printed;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "JOBCOST", nullable = false)
    private Short jobcost;

    @Column(name = "PMADJUSTNO", nullable = false, length = 16)
    private String pmadjustno;

    @Column(name = "ENTEREDBY", nullable = false, length = 8)
    private String enteredby;

    @Column(name = "DATEBUS", nullable = false, precision = 9)
    private BigDecimal datebus;

}