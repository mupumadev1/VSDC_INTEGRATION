package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "APOBL")
public class Apobl {
    @EmbeddedId
    private ApoblId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "IDRMIT", nullable = false, length = 18)
    private String idrmit;

    @Column(name = "IDORDERNBR", nullable = false, length = 22)
    private String idordernbr;

    @Column(name = "IDPONBR", nullable = false, length = 22)
    private String idponbr;

    @Column(name = "DATEINVCDU", nullable = false, precision = 9)
    private BigDecimal dateinvcdu;

    @Column(name = "IDRMITTO", nullable = false, length = 6)
    private String idrmitto;

    @Column(name = "IDTRXTYPE", nullable = false)
    private Short idtrxtype;

    @Column(name = "TXTTRXTYPE", nullable = false)
    private Short txttrxtype;

    @Column(name = "DATEBTCH", nullable = false, precision = 9)
    private BigDecimal datebtch;

    @Column(name = "CNTBTCH", nullable = false, precision = 9)
    private BigDecimal cntbtch;

    @Column(name = "CNTITEM", nullable = false, precision = 7)
    private BigDecimal cntitem;

    @Column(name = "IDVENDGRP", nullable = false, length = 6)
    private String idvendgrp;

    @Column(name = "DESCINVC", nullable = false, length = 60)
    private String descinvc;

    @Column(name = "DATEINVC", nullable = false, precision = 9)
    private BigDecimal dateinvc;

    @Column(name = "DATEASOF", nullable = false, precision = 9)
    private BigDecimal dateasof;

    @Column(name = "CODETERM", nullable = false, length = 6)
    private String codeterm;

    @Column(name = "DATEDISC", nullable = false, precision = 9)
    private BigDecimal datedisc;

    @Column(name = "CODECURN", nullable = false, length = 3)
    private String codecurn;

    @Column(name = "IDRATETYPE", nullable = false, length = 2)
    private String idratetype;

    @Column(name = "SWRATEOVRD", nullable = false)
    private Short swrateovrd;

    @Column(name = "EXCHRATEHC", nullable = false, precision = 15, scale = 7)
    private BigDecimal exchratehc;

    @Column(name = "AMTINVCHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvchc;

    @Column(name = "AMTDUEHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtduehc;

    @Column(name = "AMTTXBLHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttxblhc;

    @Column(name = "AMTNONTXHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtnontxhc;

    @Column(name = "AMTTAXHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxhc;

    @Column(name = "AMTDISCHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdischc;

    @Column(name = "AMTINVCTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvctc;

    @Column(name = "AMTDUETC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtduetc;

    @Column(name = "AMTTXBLTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttxbltc;

    @Column(name = "AMTNONTXTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtnontxtc;

    @Column(name = "AMTTAXTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxtc;

    @Column(name = "AMTDISCTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdisctc;

    @Column(name = "SWPAID", nullable = false)
    private Short swpaid;

    @Column(name = "DATELSTACT", nullable = false, precision = 9)
    private BigDecimal datelstact;

    @Column(name = "DATELSTSTM", nullable = false, precision = 9)
    private BigDecimal datelststm;

    @Column(name = "CNTTOTPAYM", nullable = false, precision = 5)
    private BigDecimal cnttotpaym;

    @Column(name = "CNTLSTPAYM", nullable = false, precision = 5)
    private BigDecimal cntlstpaym;

    @Column(name = "CNTLSTPYST", nullable = false, precision = 5)
    private BigDecimal cntlstpyst;

    @Column(name = "AMTREMIT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtremit;

    @Column(name = "CNTLASTSCH", nullable = false, precision = 5)
    private BigDecimal cntlastsch;

    @Column(name = "SWTAXOVRD", nullable = false)
    private Short swtaxovrd;

    @Column(name = "CODETAX1", nullable = false, length = 12)
    private String codetax1;

    @Column(name = "CODETAX2", nullable = false, length = 12)
    private String codetax2;

    @Column(name = "CODETAX3", nullable = false, length = 12)
    private String codetax3;

    @Column(name = "CODETAX4", nullable = false, length = 12)
    private String codetax4;

    @Column(name = "CODETAX5", nullable = false, length = 12)
    private String codetax5;

    @Column(name = "AMTBASE1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase1hc;

    @Column(name = "AMTBASE2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase2hc;

    @Column(name = "AMTBASE3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase3hc;

    @Column(name = "AMTBASE4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase4hc;

    @Column(name = "AMTBASE5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase5hc;

    @Column(name = "AMTTAX1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax1hc;

    @Column(name = "AMTTAX2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax2hc;

    @Column(name = "AMTTAX3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax3hc;

    @Column(name = "AMTTAX4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax4hc;

    @Column(name = "AMTTAX5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax5hc;

    @Column(name = "AMTBASE1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase1tc;

    @Column(name = "AMTBASE2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase2tc;

    @Column(name = "AMTBASE3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase3tc;

    @Column(name = "AMTBASE4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase4tc;

    @Column(name = "AMTBASE5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbase5tc;

    @Column(name = "AMTTAX1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax1tc;

    @Column(name = "AMTTAX2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax2tc;

    @Column(name = "AMTTAX3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax3tc;

    @Column(name = "AMTTAX4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax4tc;

    @Column(name = "AMTTAX5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax5tc;

    @Column(name = "FISCYR", nullable = false, length = 4)
    private String fiscyr;

    @Column(name = "FISCPER", nullable = false, length = 2)
    private String fiscper;

    @Column(name = "IDPREPAY", nullable = false, length = 22)
    private String idprepay;

    @Column(name = "DATEBUS", nullable = false, precision = 9)
    private BigDecimal datebus;

    @Column(name = "ID1099CLAS", nullable = false, length = 6)
    private String id1099clas;

    @Column(name = "AMT1099ORG", nullable = false, precision = 19, scale = 3)
    private BigDecimal amt1099org;

    @Column(name = "AMT1099REM", nullable = false, precision = 19, scale = 3)
    private BigDecimal amt1099rem;

    @Column(name = "RATEDATE", nullable = false, precision = 9)
    private BigDecimal ratedate;

    @Column(name = "RATEOP", nullable = false)
    private Short rateop;

    @Column(name = "YPLASTACT", nullable = false, length = 6)
    private String yplastact;

    @Column(name = "IDBANK", nullable = false, length = 8)
    private String idbank;

    @Column(name = "LONGSERIAL", nullable = false)
    private Long longserial;

    @Column(name = "POSTSEQNCE", nullable = false, precision = 9)
    private BigDecimal postseqnce;

    @Column(name = "SWJOB", nullable = false)
    private Short swjob;

    @Column(name = "SWRTG", nullable = false)
    private Short swrtg;

    @Column(name = "SWRTGOUT", nullable = false)
    private Short swrtgout;

    @Column(name = "RTGDATEDUE", nullable = false, precision = 9)
    private BigDecimal rtgdatedue;

    @Column(name = "RTGOAMTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgoamthc;

    @Column(name = "RTGAMTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamthc;

    @Column(name = "RTGOAMTTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgoamttc;

    @Column(name = "RTGAMTTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamttc;

    @Column(name = "RTGTERMS", nullable = false, length = 6)
    private String rtgterms;

    @Column(name = "SWRTGRATE", nullable = false)
    private Short swrtgrate;

    @Column(name = "RTGAPPLYTO", nullable = false, length = 22)
    private String rtgapplyto;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "SRCEAPPL", nullable = false, length = 2)
    private String srceappl;

    @Column(name = "SWPYSTTS", nullable = false)
    private Short swpystts;

    @Column(name = "DATEPYSTTS", nullable = false, precision = 9)
    private BigDecimal datepystts;

    @Column(name = "APVERSION", nullable = false, length = 3)
    private String apversion;

    @Column(name = "TYPEBTCH", nullable = false, length = 2)
    private String typebtch;

    @Column(name = "CNTOBLJ", nullable = false)
    private Integer cntoblj;

    @Column(name = "CODECURNRC", nullable = false, length = 3)
    private String codecurnrc;

    @Column(name = "RATERC", nullable = false, precision = 15, scale = 7)
    private BigDecimal raterc;

    @Column(name = "RATETYPERC", nullable = false, length = 2)
    private String ratetyperc;

    @Column(name = "RATEDATERC", nullable = false, precision = 9)
    private BigDecimal ratedaterc;

    @Column(name = "RATEOPRC", nullable = false)
    private Short rateoprc;

    @Column(name = "SWRATERC", nullable = false)
    private Short swraterc;

    @Column(name = "SWTXRTGRPT", nullable = false)
    private Short swtxrtgrpt;

    @Column(name = "CODETAXGRP", nullable = false, length = 12)
    private String codetaxgrp;

    @Column(name = "TAXVERSION", nullable = false)
    private Integer taxversion;

    @Column(name = "SWTXBSECTL", nullable = false)
    private Short swtxbsectl;

    @Column(name = "SWTXCTLRC", nullable = false)
    private Short swtxctlrc;

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

    @Column(name = "SWTAXINCL1", nullable = false)
    private Short swtaxincl1;

    @Column(name = "SWTAXINCL2", nullable = false)
    private Short swtaxincl2;

    @Column(name = "SWTAXINCL3", nullable = false)
    private Short swtaxincl3;

    @Column(name = "SWTAXINCL4", nullable = false)
    private Short swtaxincl4;

    @Column(name = "SWTAXINCL5", nullable = false)
    private Short swtaxincl5;

    @Column(name = "TXBSERT1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbsert1tc;

    @Column(name = "TXBSERT2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbsert2tc;

    @Column(name = "TXBSERT3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbsert3tc;

    @Column(name = "TXBSERT4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbsert4tc;

    @Column(name = "TXBSERT5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbsert5tc;

    @Column(name = "TXAMTRT1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt1tc;

    @Column(name = "TXAMTRT2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt2tc;

    @Column(name = "TXAMTRT3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt3tc;

    @Column(name = "TXAMTRT4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt4tc;

    @Column(name = "TXAMTRT5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt5tc;

    @Column(name = "DATEFRSTBK", nullable = false, precision = 9)
    private BigDecimal datefrstbk;

    @Column(name = "DATELSTRVL", nullable = false, precision = 9)
    private BigDecimal datelstrvl;

    @Column(name = "ORATE", nullable = false, precision = 15, scale = 7)
    private BigDecimal orate;

    @Column(name = "ORATETYPE", nullable = false, length = 2)
    private String oratetype;

    @Column(name = "ORATEDATE", nullable = false, precision = 9)
    private BigDecimal oratedate;

    @Column(name = "ORATEOP", nullable = false)
    private Short orateop;

    @Column(name = "OSWRATE", nullable = false)
    private Short oswrate;

    @Column(name = "IDACCTSET", nullable = false, length = 6)
    private String idacctset;

    @Column(name = "DATEPAID", nullable = false, precision = 9)
    private BigDecimal datepaid;

    @Column(name = "SWNONRCVBL", nullable = false)
    private Short swnonrcvbl;

    @Column(name = "OAMTWHT1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal oamtwht1tc;

    @Column(name = "OAMTWHT2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal oamtwht2tc;

    @Column(name = "OAMTWHT3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal oamtwht3tc;

    @Column(name = "OAMTWHT4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal oamtwht4tc;

    @Column(name = "OAMTWHT5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal oamtwht5tc;

}