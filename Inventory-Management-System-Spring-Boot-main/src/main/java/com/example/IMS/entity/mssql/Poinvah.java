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
@Table(name = "POINVAH")
public class Poinvah {
    @EmbeddedId
    private PoinvahId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "ISPRINTED", nullable = false)
    private Short isprinted;

    @Column(name = "INVHSEQ", nullable = false, precision = 19)
    private BigDecimal invhseq;

    @Column(name = "POSTDATE", nullable = false, precision = 9)
    private BigDecimal postdate;

    @Column(name = "DAYENDDATE", nullable = false, precision = 9)
    private BigDecimal dayenddate;

    @Column(name = "TRANSDATE", nullable = false, precision = 9)
    private BigDecimal transdate;

    @Column(name = "REFERENCE", nullable = false, length = 60)
    private String reference;

    @Column(name = "FISCYEAR", nullable = false, length = 4)
    private String fiscyear;

    @Column(name = "FISCPERIOD", nullable = false)
    private Short fiscperiod;

    @Column(name = "DESCRIPTIO", nullable = false, length = 60)
    private String descriptio;

    @Column(name = "TRANSTYPE", nullable = false)
    private Short transtype;

    @Column(name = "VENDOR", nullable = false, length = 12)
    private String vendor;

    @Column(name = "VENDORNAME", nullable = false, length = 60)
    private String vendorname;

    @Column(name = "TAXGROUP", nullable = false, length = 12)
    private String taxgroup;

    @Column(name = "TAXAUTH1", nullable = false, length = 12)
    private String taxauth1;

    @Column(name = "TAXAUTH2", nullable = false, length = 12)
    private String taxauth2;

    @Column(name = "TAXAUTH3", nullable = false, length = 12)
    private String taxauth3;

    @Column(name = "TAXAUTH4", nullable = false, length = 12)
    private String taxauth4;

    @Column(name = "TAXAUTH5", nullable = false, length = 12)
    private String taxauth5;

    @Column(name = "TAXCLASS1", nullable = false)
    private Short taxclass1;

    @Column(name = "TAXCLASS2", nullable = false)
    private Short taxclass2;

    @Column(name = "TAXCLASS3", nullable = false)
    private Short taxclass3;

    @Column(name = "TAXCLASS4", nullable = false)
    private Short taxclass4;

    @Column(name = "TAXCLASS5", nullable = false)
    private Short taxclass5;

    @Column(name = "INVNUMBER", nullable = false, length = 22)
    private String invnumber;

    @Column(name = "PONUMBER", nullable = false, length = 22)
    private String ponumber;

    @Column(name = "RCPNUMBER", nullable = false, length = 22)
    private String rcpnumber;

    @Column(name = "RCPCURR", nullable = false, length = 3)
    private String rcpcurr;

    @Column(name = "EXRATE", nullable = false, precision = 15, scale = 7)
    private BigDecimal exrate;

    @Column(name = "RATEDATE", nullable = false, precision = 9)
    private BigDecimal ratedate;

    @Column(name = "RATETYPE", nullable = false, length = 2)
    private String ratetype;

    @Column(name = "RATEOPER", nullable = false)
    private Short rateoper;

    @Column(name = "RATEOVER", nullable = false)
    private Short rateover;

    @Column(name = "SCURNDECML", nullable = false)
    private Short scurndecml;

    @Column(name = "FCDOCTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcdoctotal;

    @Column(name = "SCDOCTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal scdoctotal;

    @Column(name = "FCAPTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcaptotal;

    @Column(name = "SCAPTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal scaptotal;

    @Column(name = "F1099CLASS", nullable = false, length = 6)
    private String f1099class;

    @Column(name = "F1099AMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal f1099amt;

    @Column(name = "COMPLETE", nullable = false)
    private Short complete;

    @Column(name = "PRINTED", nullable = false)
    private Short printed;

    @Column(name = "MULTIRCP", nullable = false)
    private Short multircp;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "ONHOLD", nullable = false)
    private Short onhold;

    @Column(name = "PGMVER", nullable = false, length = 3)
    private String pgmver;

    @Column(name = "TRANSNUM", nullable = false, precision = 19)
    private BigDecimal transnum;

    @Column(name = "VERPRORATE", nullable = false)
    private Short verprorate;

    @Column(name = "HASRTG", nullable = false)
    private Short hasrtg;

    @Column(name = "RTGRATE", nullable = false)
    private Short rtgrate;

    @Column(name = "RTGBASE", nullable = false)
    private Short rtgbase;

    @Column(name = "SCRTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scrtgamt;

    @Column(name = "SCAPRTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scaprtgamt;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "HASJOB", nullable = false)
    private Short hasjob;

    @Column(name = "FCAPRTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcaprtgamt;

    @Column(name = "TRCURRENCY", nullable = false, length = 3)
    private String trcurrency;

    @Column(name = "EXRATERC", nullable = false, precision = 15, scale = 7)
    private BigDecimal exraterc;

    @Column(name = "RATEDATERC", nullable = false, precision = 9)
    private BigDecimal ratedaterc;

    @Column(name = "RATETYPERC", nullable = false, length = 2)
    private String ratetyperc;

    @Column(name = "RATEOPERRC", nullable = false)
    private Short rateoperrc;

    @Column(name = "RATERCOVER", nullable = false)
    private Short ratercover;

    @Column(name = "RCURNDECML", nullable = false)
    private Short rcurndecml;

    @Column(name = "DATEBUS", nullable = false, precision = 9)
    private BigDecimal datebus;

    @Column(name = "VDACCTSET", nullable = false, length = 6)
    private String vdacctset;

    @Column(name = "IDN", nullable = false, length = 30)
    private String idn;

    @Column(name = "CAXAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount;

}