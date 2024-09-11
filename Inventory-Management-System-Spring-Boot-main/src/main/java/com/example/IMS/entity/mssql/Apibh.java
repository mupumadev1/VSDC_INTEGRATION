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
@Table(name = "APIBH")
public class Apibh {
    @EmbeddedId
    private ApibhId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "IDVEND", nullable = false, length = 12)
    private String idvend;

    @Column(name = "IDINVC", nullable = false, length = 22)
    private String idinvc;

    @Column(name = "IDRMITTO", nullable = false, length = 6)
    private String idrmitto;

    @Column(name = "TEXTTRX", nullable = false)
    private Short texttrx;

    @Column(name = "IDTRX", nullable = false)
    private Short idtrx;

    @Column(name = "INVCSTTS", nullable = false)
    private Short invcstts;

    @Column(name = "ORDRNBR", nullable = false, length = 22)
    private String ordrnbr;

    @Column(name = "PONBR", nullable = false, length = 22)
    private String ponbr;

    @Column(name = "INVCDESC", nullable = false, length = 60)
    private String invcdesc;

    @Column(name = "SWPRTINVC", nullable = false)
    private Short swprtinvc;

    @Column(name = "INVCAPPLTO", nullable = false, length = 22)
    private String invcapplto;

    @Column(name = "IDACCTSET", nullable = false, length = 6)
    private String idacctset;

    @Column(name = "DATEINVC", nullable = false, precision = 9)
    private BigDecimal dateinvc;

    @Column(name = "DATEASOF", nullable = false, precision = 9)
    private BigDecimal dateasof;

    @Column(name = "FISCYR", nullable = false, length = 4)
    private String fiscyr;

    @Column(name = "FISCPER", nullable = false, length = 2)
    private String fiscper;

    @Column(name = "CODECURN", nullable = false, length = 3)
    private String codecurn;

    @Column(name = "RATETYPE", nullable = false, length = 2)
    private String ratetype;

    @Column(name = "SWMANRTE", nullable = false)
    private Short swmanrte;

    @Column(name = "EXCHRATEHC", nullable = false, precision = 15, scale = 7)
    private BigDecimal exchratehc;

    @Column(name = "ORIGRATEHC", nullable = false, precision = 15, scale = 7)
    private BigDecimal origratehc;

    @Column(name = "TERMCODE", nullable = false, length = 6)
    private String termcode;

    @Column(name = "SWTERMOVRD", nullable = false)
    private Short swtermovrd;

    @Column(name = "DATEDUE", nullable = false, precision = 9)
    private BigDecimal datedue;

    @Column(name = "DATEDISC", nullable = false, precision = 9)
    private BigDecimal datedisc;

    @Column(name = "PCTDISC", nullable = false, precision = 9, scale = 5)
    private BigDecimal pctdisc;

    @Column(name = "AMTDISCAVL", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdiscavl;

    @Column(name = "LASTLINE", nullable = false, precision = 5)
    private BigDecimal lastline;

    @Column(name = "SWTAXBL", nullable = false)
    private Short swtaxbl;

    @Column(name = "SWCALCTX", nullable = false)
    private Short swcalctx;

    @Column(name = "CODETAXGRP", nullable = false, length = 12)
    private String codetaxgrp;

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

    @Column(name = "BASETAX1", nullable = false, precision = 19, scale = 3)
    private BigDecimal basetax1;

    @Column(name = "BASETAX2", nullable = false, precision = 19, scale = 3)
    private BigDecimal basetax2;

    @Column(name = "BASETAX3", nullable = false, precision = 19, scale = 3)
    private BigDecimal basetax3;

    @Column(name = "BASETAX4", nullable = false, precision = 19, scale = 3)
    private BigDecimal basetax4;

    @Column(name = "BASETAX5", nullable = false, precision = 19, scale = 3)
    private BigDecimal basetax5;

    @Column(name = "AMTTAX1", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax1;

    @Column(name = "AMTTAX2", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax2;

    @Column(name = "AMTTAX3", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax3;

    @Column(name = "AMTTAX4", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax4;

    @Column(name = "AMTTAX5", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttax5;

    @Column(name = "AMT1099", nullable = false, precision = 19, scale = 3)
    private BigDecimal amt1099;

    @Column(name = "AMTDISTSET", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdistset;

    @Column(name = "AMTTAXDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxdist;

    @Column(name = "AMTINVCTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvctot;

    @Column(name = "AMTALLOCTX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtalloctx;

    @Column(name = "CNTPAYMSCH", nullable = false, precision = 5)
    private BigDecimal cntpaymsch;

    @Column(name = "AMTTOTDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttotdist;

    @Column(name = "AMTGROSDST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtgrosdst;

    @Column(name = "IDPPD", nullable = false, length = 22)
    private String idppd;

    @Column(name = "TEXTRMIT", nullable = false, length = 60)
    private String textrmit;

    @Column(name = "TEXTSTE1", nullable = false, length = 60)
    private String textste1;

    @Column(name = "TEXTSTE2", nullable = false, length = 60)
    private String textste2;

    @Column(name = "TEXTSTE3", nullable = false, length = 60)
    private String textste3;

    @Column(name = "TEXTSTE4", nullable = false, length = 60)
    private String textste4;

    @Column(name = "NAMECITY", nullable = false, length = 30)
    private String namecity;

    @Column(name = "CODESTTE", nullable = false, length = 30)
    private String codestte;

    @Column(name = "CODEPSTL", nullable = false, length = 20)
    private String codepstl;

    @Column(name = "CODECTRY", nullable = false, length = 30)
    private String codectry;

    @Column(name = "NAMECTAC", nullable = false, length = 60)
    private String namectac;

    @Column(name = "TEXTPHON", nullable = false, length = 30)
    private String textphon;

    @Column(name = "TEXTFAX", nullable = false, length = 30)
    private String textfax;

    @Column(name = "DATERATE", nullable = false, precision = 9)
    private BigDecimal daterate;

    @Column(name = "AMTRECTAX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtrectax;

    @Column(name = "CODEPAYPPD", nullable = false, precision = 5)
    private BigDecimal codepayppd;

    @Column(name = "CODEVNDGRP", nullable = false, length = 6)
    private String codevndgrp;

    @Column(name = "TERMSDESC", nullable = false, length = 60)
    private String termsdesc;

    @Column(name = "IDDISTSET", nullable = false, length = 6)
    private String iddistset;

    @Column(name = "ID1099CLAS", nullable = false, length = 6)
    private String id1099clas;

    @Column(name = "AMTTAXTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxtot;

    @Column(name = "AMTGROSTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtgrostot;

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

    @Column(name = "AMTEXPTAX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtexptax;

    @Column(name = "AMTAXTOBE", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtaxtobe;

    @Column(name = "TAXOUTBAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxoutbal;

    @Column(name = "CODEOPER", nullable = false)
    private Short codeoper;

    @Column(name = "ACCTREC1", nullable = false, length = 45)
    private String acctrec1;

    @Column(name = "ACCTREC2", nullable = false, length = 45)
    private String acctrec2;

    @Column(name = "ACCTREC3", nullable = false, length = 45)
    private String acctrec3;

    @Column(name = "ACCTREC4", nullable = false, length = 45)
    private String acctrec4;

    @Column(name = "ACCTREC5", nullable = false, length = 45)
    private String acctrec5;

    @Column(name = "ACCTEXP1", nullable = false, length = 45)
    private String acctexp1;

    @Column(name = "ACCTEXP2", nullable = false, length = 45)
    private String acctexp2;

    @Column(name = "ACCTEXP3", nullable = false, length = 45)
    private String acctexp3;

    @Column(name = "ACCTEXP4", nullable = false, length = 45)
    private String acctexp4;

    @Column(name = "ACCTEXP5", nullable = false, length = 45)
    private String acctexp5;

    @Column(name = "DRILLAPP", nullable = false, length = 2)
    private String drillapp;

    @Column(name = "DRILLTYPE", nullable = false)
    private Short drilltype;

    @Column(name = "DRILLDWNLK", nullable = false, precision = 19)
    private BigDecimal drilldwnlk;

    @Column(name = "SWJOB", nullable = false)
    private Short swjob;

    @Column(name = "AMTRECDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtrecdist;

    @Column(name = "AMTEXPDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtexpdist;

    @Column(name = "ERRBATCH", nullable = false)
    private Integer errbatch;

    @Column(name = "ERRENTRY", nullable = false)
    private Integer errentry;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "CTACPHONE", nullable = false, length = 30)
    private String ctacphone;

    @Column(name = "CTACFAX", nullable = false, length = 30)
    private String ctacfax;

    @Column(name = "CTACEMAIL", nullable = false, length = 50)
    private String ctacemail;

    @Column(name = "AMTPPD", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtppd;

    @Column(name = "IDSTDINVC", nullable = false, length = 16)
    private String idstdinvc;

    @Column(name = "DATEPRCS", nullable = false, precision = 9)
    private BigDecimal dateprcs;

    @Column(name = "AMTDSBWTAX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdsbwtax;

    @Column(name = "AMTDSBNTAX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdsbntax;

    @Column(name = "AMTDSCBASE", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdscbase;

    @Column(name = "SWRTGINVC", nullable = false)
    private Short swrtginvc;

    @Column(name = "RTGAPPLYTO", nullable = false, length = 22)
    private String rtgapplyto;

    @Column(name = "SWRTG", nullable = false)
    private Short swrtg;

    @Column(name = "RTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamt;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "RTGDAYS", nullable = false)
    private Short rtgdays;

    @Column(name = "RTGDATEDUE", nullable = false, precision = 9)
    private BigDecimal rtgdatedue;

    @Column(name = "RTGTERMS", nullable = false, length = 6)
    private String rtgterms;

    @Column(name = "SWRTGDDTOV", nullable = false)
    private Short swrtgddtov;

    @Column(name = "SWRTGAMTOV", nullable = false)
    private Short swrtgamtov;

    @Column(name = "SWRTGRATE", nullable = false)
    private Short swrtgrate;

    @Column(name = "SWTXBSECTL", nullable = false)
    private Short swtxbsectl;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "ORIGCOMP", nullable = false, length = 6)
    private String origcomp;

    @Column(name = "DETAILCNT", nullable = false)
    private Integer detailcnt;

    @Column(name = "SRCEAPPL", nullable = false, length = 2)
    private String srceappl;

    @Column(name = "SWHOLD", nullable = false)
    private Short swhold;

    @Column(name = "APVERSION", nullable = false, length = 3)
    private String apversion;

    @Column(name = "TAXVERSION", nullable = false)
    private Integer taxversion;

    @Column(name = "SWTXRTGRPT", nullable = false)
    private Short swtxrtgrpt;

    @Column(name = "CODECURNRC", nullable = false, length = 3)
    private String codecurnrc;

    @Column(name = "SWTXCTLRC", nullable = false)
    private Short swtxctlrc;

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

    @Column(name = "TXAMT1RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt1rc;

    @Column(name = "TXAMT2RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt2rc;

    @Column(name = "TXAMT3RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt3rc;

    @Column(name = "TXAMT4RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt4rc;

    @Column(name = "TXAMT5RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt5rc;

    @Column(name = "TXTOTRC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txtotrc;

    @Column(name = "TXALLRC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txallrc;

    @Column(name = "TXEXPRC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexprc;

    @Column(name = "TXRECRC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecrc;

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

    @Column(name = "TXBSE1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbse1hc;

    @Column(name = "TXBSE2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbse2hc;

    @Column(name = "TXBSE3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbse3hc;

    @Column(name = "TXBSE4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbse4hc;

    @Column(name = "TXBSE5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbse5hc;

    @Column(name = "TXAMT1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt1hc;

    @Column(name = "TXAMT2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt2hc;

    @Column(name = "TXAMT3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt3hc;

    @Column(name = "TXAMT4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt4hc;

    @Column(name = "TXAMT5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamt5hc;

    @Column(name = "AMTGROSHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtgroshc;

    @Column(name = "RTGAMTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamthc;

    @Column(name = "AMTDISCHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdischc;

    @Column(name = "AMT1099HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amt1099hc;

    @Column(name = "AMTPPDHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtppdhc;

    @Column(name = "AMTDUETC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtduetc;

    @Column(name = "AMTDUEHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtduehc;

    @Column(name = "TEXTVEN", nullable = false, length = 60)
    private String textven;

    @Column(name = "ENTEREDBY", nullable = false, length = 8)
    private String enteredby;

    @Column(name = "DATEBUS", nullable = false, precision = 9)
    private BigDecimal datebus;

    @Column(name = "IDN", nullable = false, length = 30)
    private String idn;

    @Column(name = "AMTWHT1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwht1tc;

    @Column(name = "AMTWHT2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwht2tc;

    @Column(name = "AMTWHT3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwht3tc;

    @Column(name = "AMTWHT4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwht4tc;

    @Column(name = "AMTWHT5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwht5tc;

    @Column(name = "AMTCXBS1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxbs1tc;

    @Column(name = "AMTCXBS2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxbs2tc;

    @Column(name = "AMTCXBS3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxbs3tc;

    @Column(name = "AMTCXBS4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxbs4tc;

    @Column(name = "AMTCXBS5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxbs5tc;

    @Column(name = "AMTCXTX1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxtx1tc;

    @Column(name = "AMTCXTX2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxtx2tc;

    @Column(name = "AMTCXTX3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxtx3tc;

    @Column(name = "AMTCXTX4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxtx4tc;

    @Column(name = "AMTCXTX5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcxtx5tc;

}