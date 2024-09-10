package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "PORCPAH", schema = "dbo")
public class Porcpah {
    @EmbeddedId
    private PorcpahId id;

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

    @Column(name = "RCPHSEQ", nullable = false, precision = 19)
    private BigDecimal rcphseq;

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

    @Column(name = "COMPLETE", nullable = false)
    private Short complete;

    @Column(name = "PRINTED", nullable = false)
    private Short printed;

    @Column(name = "MULTIPOR", nullable = false)
    private Short multipor;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

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

    @Column(name = "HASJOB", nullable = false)
    private Short hasjob;

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

    @Column(name = "CAXAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount;

    public Porcpah(PorcpahId id, BigDecimal audtdate, BigDecimal audttime, String audtuser, String audtorg, Short isprinted, BigDecimal rcphseq, BigDecimal postdate, BigDecimal dayenddate, BigDecimal transdate, String reference, String fiscyear, Short fiscperiod, String descriptio, Short transtype, String vendor, String vendorname, String taxgroup, String taxauth1, String taxauth2, String taxauth3, String taxauth4, String taxauth5, Short taxclass1, Short taxclass2, Short taxclass3, Short taxclass4, Short taxclass5, String ponumber, String rcpnumber, String rcpcurr, BigDecimal exrate, BigDecimal ratedate, String ratetype, Short rateoper, Short rateover, Short scurndecml, BigDecimal fcdoctotal, BigDecimal scdoctotal, Short complete, Short printed, Short multipor, Integer values, String pgmver, BigDecimal transnum, Short verprorate, Short hasrtg, Short rtgrate, Short rtgbase, BigDecimal scrtgamt, Short hasjob, String trcurrency, BigDecimal exraterc, BigDecimal ratedaterc, String ratetyperc, Short rateoperrc, Short ratercover, Short rcurndecml, BigDecimal datebus, String vdacctset, BigDecimal caxamount) {
        this.id = id;
        this.audtdate = audtdate;
        this.audttime = audttime;
        this.audtuser = audtuser;
        this.audtorg = audtorg;
        this.isprinted = isprinted;
        this.rcphseq = rcphseq;
        this.postdate = postdate;
        this.dayenddate = dayenddate;
        this.transdate = transdate;
        this.reference = reference;
        this.fiscyear = fiscyear;
        this.fiscperiod = fiscperiod;
        this.descriptio = descriptio;
        this.transtype = transtype;
        this.vendor = vendor;
        this.vendorname = vendorname;
        this.taxgroup = taxgroup;
        this.taxauth1 = taxauth1;
        this.taxauth2 = taxauth2;
        this.taxauth3 = taxauth3;
        this.taxauth4 = taxauth4;
        this.taxauth5 = taxauth5;
        this.taxclass1 = taxclass1;
        this.taxclass2 = taxclass2;
        this.taxclass3 = taxclass3;
        this.taxclass4 = taxclass4;
        this.taxclass5 = taxclass5;
        this.ponumber = ponumber;
        this.rcpnumber = rcpnumber;
        this.rcpcurr = rcpcurr;
        this.exrate = exrate;
        this.ratedate = ratedate;
        this.ratetype = ratetype;
        this.rateoper = rateoper;
        this.rateover = rateover;
        this.scurndecml = scurndecml;
        this.fcdoctotal = fcdoctotal;
        this.scdoctotal = scdoctotal;
        this.complete = complete;
        this.printed = printed;
        this.multipor = multipor;
        this.values = values;
        this.pgmver = pgmver;
        this.transnum = transnum;
        this.verprorate = verprorate;
        this.hasrtg = hasrtg;
        this.rtgrate = rtgrate;
        this.rtgbase = rtgbase;
        this.scrtgamt = scrtgamt;
        this.hasjob = hasjob;
        this.trcurrency = trcurrency;
        this.exraterc = exraterc;
        this.ratedaterc = ratedaterc;
        this.ratetyperc = ratetyperc;
        this.rateoperrc = rateoperrc;
        this.ratercover = ratercover;
        this.rcurndecml = rcurndecml;
        this.datebus = datebus;
        this.vdacctset = vdacctset;
        this.caxamount = caxamount;
    }
}