package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "POPORH1", schema = "dbo")
public class Poporh1 {
    @Id
    @Column(name = "PORHSEQ", nullable = false, precision = 19)
    private BigDecimal id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "NEXTLSEQ", nullable = false, precision = 19)
    private BigDecimal nextlseq;

    @Column(name = "LINES", nullable = false)
    private Integer lines;

    @Column(name = "LINESCMPL", nullable = false)
    private Integer linescmpl;

    @Column(name = "TAXLINES", nullable = false)
    private Integer taxlines;

    @Column(name = "RQNS", nullable = false)
    private Integer rqns;

    @Column(name = "RQNSCMPL", nullable = false)
    private Integer rqnscmpl;

    @Column(name = "ISPRINTED", nullable = false)
    private Short isprinted;

    @Column(name = "TAXAUTOCAL", nullable = false)
    private Short taxautocal;

    @Column(name = "LABELPRINT", nullable = false)
    private Short labelprint;

    @Column(name = "LABELCOUNT", nullable = false)
    private Short labelcount;

    @Column(name = "ISCOMPLETE", nullable = false)
    private Short iscomplete;

    @Column(name = "DTCOMPLETE", nullable = false, precision = 9)
    private BigDecimal dtcomplete;

    @Column(name = "POSTDATE", nullable = false, precision = 9)
    private BigDecimal postdate;

    @Column(name = "\"DATE\"", nullable = false, precision = 9)
    private BigDecimal date;

    @Column(name = "PONUMBER", nullable = false, length = 22)
    private String ponumber;

    @Column(name = "TEMPLATE", nullable = false, length = 6)
    private String template;

    @Column(name = "FOBPOINT", nullable = false, length = 60)
    private String fobpoint;

    @Column(name = "VDCODE", nullable = false, length = 12)
    private String vdcode;

    @Column(name = "VDEXISTS", nullable = false)
    private Short vdexists;

    @Column(name = "VDNAME", nullable = false, length = 60)
    private String vdname;

    @Column(name = "VDADDRESS1", nullable = false, length = 60)
    private String vdaddress1;

    @Column(name = "VDADDRESS2", nullable = false, length = 60)
    private String vdaddress2;

    @Column(name = "VDADDRESS3", nullable = false, length = 60)
    private String vdaddress3;

    @Column(name = "VDADDRESS4", nullable = false, length = 60)
    private String vdaddress4;

    @Column(name = "VDCITY", nullable = false, length = 30)
    private String vdcity;

    @Column(name = "VDSTATE", nullable = false, length = 30)
    private String vdstate;

    @Column(name = "VDZIP", nullable = false, length = 20)
    private String vdzip;

    @Column(name = "VDCOUNTRY", nullable = false, length = 30)
    private String vdcountry;

    @Column(name = "VDPHONE", nullable = false, length = 30)
    private String vdphone;

    @Column(name = "VDFAX", nullable = false, length = 30)
    private String vdfax;

    @Column(name = "VDCONTACT", nullable = false, length = 60)
    private String vdcontact;

    @Column(name = "TERMSCODE", nullable = false, length = 6)
    private String termscode;

    @Column(name = "HASRQNDATA", nullable = false)
    private Short hasrqndata;

    @Column(name = "PORTYPE", nullable = false)
    private Short portype;

    @Column(name = "ONHOLD", nullable = false)
    private Short onhold;

    @Column(name = "ORDEREDON", nullable = false, precision = 9)
    private BigDecimal orderedon;

    @Column(name = "EXPARRIVAL", nullable = false, precision = 9)
    private BigDecimal exparrival;

    @Column(name = "VCORIGINAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal vcoriginal;

    @Column(name = "VCAVAILABL", nullable = false, precision = 19, scale = 3)
    private BigDecimal vcavailabl;

    @Column(name = "DESCRIPTIO", nullable = false, length = 60)
    private String descriptio;

    @Column(name = "REFERENCE", nullable = false, length = 60)
    private String reference;

    @Column(name = "COMMENT", nullable = false, length = 250)
    private String comment;

    @Column(name = "VIACODE", nullable = false, length = 6)
    private String viacode;

    @Column(name = "VIANAME", nullable = false, length = 60)
    private String vianame;

    @Column(name = "LASTRECEIP", nullable = false, length = 22)
    private String lastreceip;

    @Column(name = "RCPDATE", nullable = false, precision = 9)
    private BigDecimal rcpdate;

    @Column(name = "RCPCOUNT", nullable = false)
    private Short rcpcount;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    private String currency;

    @Column(name = "RATE", nullable = false, precision = 15, scale = 7)
    private BigDecimal rate;

    @Column(name = "SPREAD", nullable = false, precision = 15, scale = 7)
    private BigDecimal spread;

    @Column(name = "RATETYPE", nullable = false, length = 2)
    private String ratetype;

    @Column(name = "RATEMATCH", nullable = false)
    private Short ratematch;

    @Column(name = "RATEDATE", nullable = false, precision = 9)
    private BigDecimal ratedate;

    @Column(name = "RATEOPER", nullable = false)
    private Short rateoper;

    @Column(name = "RATEOVER", nullable = false)
    private Short rateover;

    @Column(name = "SCURNDECML", nullable = false)
    private Short scurndecml;

    @Column(name = "EXTWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal extweight;

    @Column(name = "EXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal extended;

    @Column(name = "DOCTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal doctotal;

    @Column(name = "EXTRECEIVE", nullable = false, precision = 19, scale = 3)
    private BigDecimal extreceive;

    @Column(name = "EXTCANCEL", nullable = false, precision = 19, scale = 3)
    private BigDecimal extcancel;

    @Column(name = "OQORDERED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqordered;

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

    @Column(name = "TAXBASE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxbase1;

    @Column(name = "TAXBASE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxbase2;

    @Column(name = "TAXBASE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxbase3;

    @Column(name = "TAXBASE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxbase4;

    @Column(name = "TAXBASE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxbase5;

    @Column(name = "TXINCLUDE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal txinclude1;

    @Column(name = "TXINCLUDE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal txinclude2;

    @Column(name = "TXINCLUDE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal txinclude3;

    @Column(name = "TXINCLUDE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal txinclude4;

    @Column(name = "TXINCLUDE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal txinclude5;

    @Column(name = "TXEXCLUDE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexclude1;

    @Column(name = "TXEXCLUDE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexclude2;

    @Column(name = "TXEXCLUDE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexclude3;

    @Column(name = "TXEXCLUDE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexclude4;

    @Column(name = "TXEXCLUDE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexclude5;

    @Column(name = "TAXAMOUNT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxamount1;

    @Column(name = "TAXAMOUNT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxamount2;

    @Column(name = "TAXAMOUNT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxamount3;

    @Column(name = "TAXAMOUNT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxamount4;

    @Column(name = "TAXAMOUNT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxamount5;

    @Column(name = "TXRECVAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecvamt1;

    @Column(name = "TXRECVAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecvamt2;

    @Column(name = "TXRECVAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecvamt3;

    @Column(name = "TXRECVAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecvamt4;

    @Column(name = "TXRECVAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecvamt5;

    @Column(name = "TXEXPSAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexpsamt1;

    @Column(name = "TXEXPSAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexpsamt2;

    @Column(name = "TXEXPSAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexpsamt3;

    @Column(name = "TXEXPSAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexpsamt4;

    @Column(name = "TXEXPSAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexpsamt5;

    @Column(name = "TXALLOAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal txalloamt1;

    @Column(name = "TXALLOAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal txalloamt2;

    @Column(name = "TXALLOAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal txalloamt3;

    @Column(name = "TXALLOAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal txalloamt4;

    @Column(name = "TXALLOAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal txalloamt5;

    @Column(name = "TXBASEALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbaseallo;

    @Column(name = "TXINCLUDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal txincluded;

    @Column(name = "TXEXCLUDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexcluded;

    @Column(name = "TAXAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal taxamount;

    @Column(name = "TXRECVAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrecvamt;

    @Column(name = "TXEXPSAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexpsamt;

    @Column(name = "TXALLOAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal txalloamt;

    @Column(name = "DOCSOURCE", nullable = false)
    private Short docsource;

    @Column(name = "VDEMAIL", nullable = false, length = 50)
    private String vdemail;

    @Column(name = "VDPHONEC", nullable = false, length = 30)
    private String vdphonec;

    @Column(name = "VDFAXC", nullable = false, length = 30)
    private String vdfaxc;

    @Column(name = "VDEMAILC", nullable = false, length = 50)
    private String vdemailc;

    @Column(name = "DISCPCT", nullable = false, precision = 9, scale = 5)
    private BigDecimal discpct;

    @Column(name = "DISCOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal discount;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "RQNNUMBER", nullable = false, length = 22)
    private String rqnnumber;

    @Column(name = "RQNHSEQ", nullable = false, precision = 19)
    private BigDecimal rqnhseq;

    @Column(name = "SCAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scamount;

    @Column(name = "FCAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcamount;

    @Column(name = "JOBLINES", nullable = false)
    private Integer joblines;

    @Column(name = "TRCURRENCY", nullable = false, length = 3)
    private String trcurrency;

    @Column(name = "RATERC", nullable = false, precision = 15, scale = 7)
    private BigDecimal raterc;

    @Column(name = "SPREADRC", nullable = false, precision = 15, scale = 7)
    private BigDecimal spreadrc;

    @Column(name = "RATETYPERC", nullable = false, length = 2)
    private String ratetyperc;

    @Column(name = "RATEMTCHRC", nullable = false)
    private Short ratemtchrc;

    @Column(name = "RATEDATERC", nullable = false, precision = 9)
    private BigDecimal ratedaterc;

    @Column(name = "RATEOPERRC", nullable = false)
    private Short rateoperrc;

    @Column(name = "RATERCOVER", nullable = false)
    private Short ratercover;

    @Column(name = "RCURNDECML", nullable = false)
    private Short rcurndecml;

    @Column(name = "TARAMOUNT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal taramount1;

    @Column(name = "TARAMOUNT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal taramount2;

    @Column(name = "TARAMOUNT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal taramount3;

    @Column(name = "TARAMOUNT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal taramount4;

    @Column(name = "TARAMOUNT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal taramount5;

    @Column(name = "TRINCLUDE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal trinclude1;

    @Column(name = "TRINCLUDE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal trinclude2;

    @Column(name = "TRINCLUDE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal trinclude3;

    @Column(name = "TRINCLUDE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal trinclude4;

    @Column(name = "TRINCLUDE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal trinclude5;

    @Column(name = "TREXCLUDE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexclude1;

    @Column(name = "TREXCLUDE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexclude2;

    @Column(name = "TREXCLUDE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexclude3;

    @Column(name = "TREXCLUDE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexclude4;

    @Column(name = "TREXCLUDE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexclude5;

    @Column(name = "TRRECVAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal trrecvamt1;

    @Column(name = "TRRECVAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal trrecvamt2;

    @Column(name = "TRRECVAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal trrecvamt3;

    @Column(name = "TRRECVAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal trrecvamt4;

    @Column(name = "TRRECVAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal trrecvamt5;

    @Column(name = "TREXPSAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexpsamt1;

    @Column(name = "TREXPSAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexpsamt2;

    @Column(name = "TREXPSAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexpsamt3;

    @Column(name = "TREXPSAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexpsamt4;

    @Column(name = "TREXPSAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal trexpsamt5;

    @Column(name = "TRALLOAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal tralloamt1;

    @Column(name = "TRALLOAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal tralloamt2;

    @Column(name = "TRALLOAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal tralloamt3;

    @Column(name = "TRALLOAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal tralloamt4;

    @Column(name = "TRALLOAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal tralloamt5;

}