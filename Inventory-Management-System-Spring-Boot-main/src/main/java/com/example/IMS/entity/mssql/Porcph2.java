package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PORCPH2", schema = "dbo")
public class Porcph2 {
    @Id
    @Column(name = "RCPHSEQ", nullable = false, precision = 19)
    private BigDecimal id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "BTCODE", nullable = false, length = 6)
    private String btcode;

    @Column(name = "BTDESC", nullable = false, length = 60)
    private String btdesc;

    @Column(name = "BTADDRESS1", nullable = false, length = 60)
    private String btaddress1;

    @Column(name = "BTADDRESS2", nullable = false, length = 60)
    private String btaddress2;

    @Column(name = "BTADDRESS3", nullable = false, length = 60)
    private String btaddress3;

    @Column(name = "BTADDRESS4", nullable = false, length = 60)
    private String btaddress4;

    @Column(name = "BTCITY", nullable = false, length = 30)
    private String btcity;

    @Column(name = "BTSTATE", nullable = false, length = 30)
    private String btstate;

    @Column(name = "BTZIP", nullable = false, length = 20)
    private String btzip;

    @Column(name = "BTCOUNTRY", nullable = false, length = 30)
    private String btcountry;

    @Column(name = "BTPHONE", nullable = false, length = 30)
    private String btphone;

    @Column(name = "BTFAX", nullable = false, length = 30)
    private String btfax;

    @Column(name = "BTCONTACT", nullable = false, length = 60)
    private String btcontact;

    @Column(name = "STCODE", nullable = false, length = 6)
    private String stcode;

    @Column(name = "STDESC", nullable = false, length = 60)
    private String stdesc;

    @Column(name = "STADDRESS1", nullable = false, length = 60)
    private String staddress1;

    @Column(name = "STADDRESS2", nullable = false, length = 60)
    private String staddress2;

    @Column(name = "STADDRESS3", nullable = false, length = 60)
    private String staddress3;

    @Column(name = "STADDRESS4", nullable = false, length = 60)
    private String staddress4;

    @Column(name = "STCITY", nullable = false, length = 30)
    private String stcity;

    @Column(name = "STSTATE", nullable = false, length = 30)
    private String ststate;

    @Column(name = "STZIP", nullable = false, length = 20)
    private String stzip;

    @Column(name = "STCOUNTRY", nullable = false, length = 30)
    private String stcountry;

    @Column(name = "STPHONE", nullable = false, length = 30)
    private String stphone;

    @Column(name = "STFAX", nullable = false, length = 30)
    private String stfax;

    @Column(name = "STCONTACT", nullable = false, length = 60)
    private String stcontact;

    @Column(name = "PDRATE", nullable = false, precision = 15, scale = 7)
    private BigDecimal pdrate;

    @Column(name = "PDRATETYPE", nullable = false, length = 2)
    private String pdratetype;

    @Column(name = "PDRATEDATE", nullable = false, precision = 9)
    private BigDecimal pdratedate;

    @Column(name = "PDRATEOPER", nullable = false)
    private Short pdrateoper;

    @Column(name = "PDRATEOVER", nullable = false)
    private Short pdrateover;

    @Column(name = "BTEMAIL", nullable = false, length = 50)
    private String btemail;

    @Column(name = "BTPHONEC", nullable = false, length = 30)
    private String btphonec;

    @Column(name = "BTFAXC", nullable = false, length = 30)
    private String btfaxc;

    @Column(name = "BTEMAILC", nullable = false, length = 50)
    private String btemailc;

    @Column(name = "STEMAIL", nullable = false, length = 50)
    private String stemail;

    @Column(name = "STPHONEC", nullable = false, length = 30)
    private String stphonec;

    @Column(name = "STFAXC", nullable = false, length = 30)
    private String stfaxc;

    @Column(name = "STEMAILC", nullable = false, length = 50)
    private String stemailc;

    @Column(name = "PDRATERC", nullable = false, precision = 15, scale = 7)
    private BigDecimal pdraterc;

    @Column(name = "PDRATTYPRC", nullable = false, length = 2)
    private String pdrattyprc;

    @Column(name = "PDRATEDTRC", nullable = false, precision = 9)
    private BigDecimal pdratedtrc;

    @Column(name = "PDRATEOPRC", nullable = false)
    private Short pdrateoprc;

    @Column(name = "PDRATERCOV", nullable = false)
    private Short pdratercov;

    @Column(name = "VDACCTSET", nullable = false, length = 6)
    private String vdacctset;

    @Column(name = "DATEBUS", nullable = false, precision = 9)
    private BigDecimal datebus;

    @Column(name = "ENTEREDBY", nullable = false, length = 8)
    private String enteredby;

    @Column(name = "DETAILNEXT", nullable = false)
    private Short detailnext;

    @Column(name = "CAXBASE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxbase1;

    @Column(name = "CAXBASE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxbase2;

    @Column(name = "CAXBASE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxbase3;

    @Column(name = "CAXBASE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxbase4;

    @Column(name = "CAXBASE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxbase5;

    @Column(name = "CAXDTAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxdtamt1;

    @Column(name = "CAXDTAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxdtamt2;

    @Column(name = "CAXDTAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxdtamt3;

    @Column(name = "CAXDTAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxdtamt4;

    @Column(name = "CAXDTAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxdtamt5;

    @Column(name = "CAXAPPLY1", nullable = false)
    private Short caxapply1;

    @Column(name = "CAXAPPLY2", nullable = false)
    private Short caxapply2;

    @Column(name = "CAXAPPLY3", nullable = false)
    private Short caxapply3;

    @Column(name = "CAXAPPLY4", nullable = false)
    private Short caxapply4;

    @Column(name = "CAXAPPLY5", nullable = false)
    private Short caxapply5;

    @Column(name = "CAXAMOUNT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount1;

    @Column(name = "CAXAMOUNT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount2;

    @Column(name = "CAXAMOUNT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount3;

    @Column(name = "CAXAMOUNT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount4;

    @Column(name = "CAXAMOUNT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount5;

}