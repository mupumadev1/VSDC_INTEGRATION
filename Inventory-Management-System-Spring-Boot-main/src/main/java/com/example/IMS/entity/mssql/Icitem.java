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
@Table(name = "ICITEM", schema = "dbo")
public class Icitem {
    @Id
    @Column(name = "ITEMNO", nullable = false, length = 24)
    private String itemno;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "ALTSET", nullable = false)
    private Integer altset;

    @Column(name = "\"DESC\"", nullable = false, length = 60)
    private String desc;

    @Column(name = "DATELASTMN", nullable = false, precision = 9)
    private BigDecimal datelastmn;

    @Column(name = "INACTIVE", nullable = false)
    private Short inactive;

    @Column(name = "ITEMBRKID", nullable = false, length = 6)
    private String itembrkid;

    @Column(name = "FMTITEMNO", nullable = false, length = 24)
    private String fmtitemno;

    @Column(name = "CATEGORY", nullable = false, length = 6)
    private String category;

    @Column(name = "CNTLACCT", nullable = false, length = 6)
    private String cntlacct;

    @Column(name = "STOCKITEM", nullable = false)
    private Short stockitem;

    @Column(name = "STOCKUNIT", nullable = false, length = 10)
    private String stockunit;

    @Column(name = "DEFPRICLST", nullable = false, length = 6)
    private String defpriclst;

    @Column(name = "UNITWGT", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitwgt;

    @Column(name = "PICKINGSEQ", nullable = false, length = 10)
    private String pickingseq;

    @Column(name = "SERIALNO", nullable = false)
    private Short serialno;

    @Column(name = "COMMODIM", nullable = false, length = 16)
    private String commodim;

    @Column(name = "DATEINACTV", nullable = false, precision = 9)
    private BigDecimal dateinactv;

    @Column(name = "SEGMENT1", nullable = false, length = 24)
    private String segment1;

    @Column(name = "SEGMENT2", nullable = false, length = 24)
    private String segment2;

    @Column(name = "SEGMENT3", nullable = false, length = 24)
    private String segment3;

    @Column(name = "SEGMENT4", nullable = false, length = 24)
    private String segment4;

    @Column(name = "SEGMENT5", nullable = false, length = 24)
    private String segment5;

    @Column(name = "SEGMENT6", nullable = false, length = 24)
    private String segment6;

    @Column(name = "SEGMENT7", nullable = false, length = 24)
    private String segment7;

    @Column(name = "SEGMENT8", nullable = false, length = 24)
    private String segment8;

    @Column(name = "SEGMENT9", nullable = false, length = 24)
    private String segment9;

    @Column(name = "SEGMENT10", nullable = false, length = 24)
    private String segment10;

    @Column(name = "COMMENT1", nullable = false, length = 80)
    private String comment1;

    @Column(name = "COMMENT2", nullable = false, length = 80)
    private String comment2;

    @Column(name = "COMMENT3", nullable = false, length = 80)
    private String comment3;

    @Column(name = "COMMENT4", nullable = false, length = 80)
    private String comment4;

    @Column(name = "ALLOWONWEB", nullable = false)
    private Short allowonweb;

    @Column(name = "KITTING", nullable = false)
    private Short kitting;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "DEFKITNO", nullable = false, length = 6)
    private String defkitno;

    @Column(name = "SELLABLE", nullable = false)
    private Short sellable;

    @Column(name = "WEIGHTUNIT", nullable = false, length = 10)
    private String weightunit;

    @Column(name = "SERIALMASK", nullable = false, length = 6)
    private String serialmask;

    @Column(name = "NEXTSERFMT", nullable = false, length = 40)
    private String nextserfmt;

    @Column(name = "SUSEEXPDAY", nullable = false)
    private Short suseexpday;

    @Column(name = "SEXPDAYS", nullable = false)
    private Short sexpdays;

    @Column(name = "SDIFQTYOK", nullable = false)
    private Short sdifqtyok;

    @Column(name = "SVALUES", nullable = false)
    private Integer svalues;

    @Column(name = "SWARYCODE", nullable = false, length = 6)
    private String swarycode;

    @Column(name = "SCONTCODE", nullable = false, length = 6)
    private String scontcode;

    @Column(name = "SCONTRECE", nullable = false)
    private Short scontrece;

    @Column(name = "SWARYSOLD", nullable = false)
    private Short swarysold;

    @Column(name = "SWARYREG", nullable = false)
    private Short swaryreg;

    @Column(name = "LOTITEM", nullable = false)
    private Short lotitem;

    @Column(name = "LOTMASK", nullable = false, length = 6)
    private String lotmask;

    @Column(name = "NEXTLOTFMT", nullable = false, length = 40)
    private String nextlotfmt;

    @Column(name = "LUSEEXPDAY", nullable = false)
    private Short luseexpday;

    @Column(name = "LEXPDAYS", nullable = false)
    private Short lexpdays;

    @Column(name = "LUSEQRNDAY", nullable = false)
    private Short luseqrnday;

    @Column(name = "LQRNDAYS", nullable = false)
    private Short lqrndays;

    @Column(name = "LDIFQTYOK", nullable = false)
    private Short ldifqtyok;

    @Column(name = "LVALUES", nullable = false)
    private Integer lvalues;

    @Column(name = "LWARYCODE", nullable = false, length = 6)
    private String lwarycode;

    @Column(name = "LCONTCODE", nullable = false, length = 6)
    private String lcontcode;

    @Column(name = "LCONTRECE", nullable = false)
    private Short lcontrece;

    @Column(name = "LWARYSOLD", nullable = false)
    private Short lwarysold;

    @Column(name = "PREVENDTY", nullable = false)
    private Short prevendty;

    @Column(name = "DEFBOMNO", nullable = false, length = 6)
    private String defbomno;

    @Column(name = "SEASONAL", nullable = false)
    private Short seasonal;

    @Column(name = "TARIFFCODE", nullable = false, length = 20)
    private String tariffcode;

}