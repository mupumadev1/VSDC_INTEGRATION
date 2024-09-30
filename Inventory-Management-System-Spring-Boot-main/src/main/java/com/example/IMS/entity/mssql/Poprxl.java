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
@Table(name = "POPRXL")
public class Poprxl {
    @EmbeddedId
    private PoprxlId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "SCEXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal scextended;

    @Column(name = "WTEXTENDED", nullable = false, precision = 19, scale = 4)
    private BigDecimal wtextended;

    @Column(name = "RQEXTENDED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqextended;

    @Column(name = "SCRETURNED", nullable = false, precision = 19, scale = 3)
    private BigDecimal screturned;

    @Column(name = "WTRETURNED", nullable = false, precision = 19, scale = 4)
    private BigDecimal wtreturned;

    @Column(name = "RQRETURNED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqreturned;

    @Column(name = "SCEXTENDPR", nullable = false, precision = 19, scale = 3)
    private BigDecimal scextendpr;

    @Column(name = "WTEXTENDPR", nullable = false, precision = 19, scale = 4)
    private BigDecimal wtextendpr;

    @Column(name = "RQEXTENDPR", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqextendpr;

    @Column(name = "SCRETURNPR", nullable = false, precision = 19, scale = 3)
    private BigDecimal screturnpr;

    @Column(name = "WTRETURNPR", nullable = false, precision = 19, scale = 4)
    private BigDecimal wtreturnpr;

    @Column(name = "RQRETURNPR", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqreturnpr;

    @Column(name = "LINEFLAGS", nullable = false)
    private Integer lineflags;

    @Column(name = "FORPRORATE", nullable = false)
    private Short forprorate;

    @Column(name = "SCEXTENDPB", nullable = false, precision = 19, scale = 3)
    private BigDecimal scextendpb;

    @Column(name = "WTEXTENDPB", nullable = false, precision = 19, scale = 4)
    private BigDecimal wtextendpb;

    @Column(name = "RQEXTENDPB", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqextendpb;

    @Column(name = "SCRETURNPB", nullable = false, precision = 19, scale = 3)
    private BigDecimal screturnpb;

    @Column(name = "WTRETURNPB", nullable = false, precision = 19, scale = 4)
    private BigDecimal wtreturnpb;

    @Column(name = "RQRETURNPB", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqreturnpb;

}