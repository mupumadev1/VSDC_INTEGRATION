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
@Table(name = "POOPT", schema = "dbo")
public class Poopt {
    @Id
    @Column(name = "\"DUMMY\"", nullable = false)
    private Short id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "NEXTSEQ", nullable = false, precision = 19)
    private BigDecimal nextseq;

    @Column(name = "PHONE", nullable = false, length = 30)
    private String phone;

    @Column(name = "FAX", nullable = false, length = 30)
    private String fax;

    @Column(name = "CONTACT", nullable = false, length = 60)
    private String contact;

    @Column(name = "RATETYPE", nullable = false, length = 2)
    private String ratetype;

    @Column(name = "NOXITEMPER", nullable = false)
    private Short noxitemper;

    @Column(name = "ORDHIST", nullable = false)
    private Short ordhist;

    @Column(name = "TRANHIST", nullable = false)
    private Short tranhist;

    @Column(name = "KEEPSTAT", nullable = false)
    private Short keepstat;

    @Column(name = "EDITSTAT", nullable = false)
    private Short editstat;

    @Column(name = "ORDYEAR", nullable = false)
    private Short ordyear;

    @Column(name = "ORDPERIOD", nullable = false)
    private Short ordperiod;

    @Column(name = "STATYEAR", nullable = false)
    private Short statyear;

    @Column(name = "STATPERIOD", nullable = false)
    private Short statperiod;

    @Column(name = "AGEDAYS1", nullable = false)
    private Short agedays1;

    @Column(name = "AGEDAYS2", nullable = false)
    private Short agedays2;

    @Column(name = "AGEDAYS3", nullable = false)
    private Short agedays3;

    @Column(name = "RQNNUMBERL", nullable = false)
    private Short rqnnumberl;

    @Column(name = "RQNPREFIXD", nullable = false, length = 6)
    private String rqnprefixd;

    @Column(name = "RQNBODYD", nullable = false, length = 22)
    private String rqnbodyd;

    @Column(name = "PORNUMBERL", nullable = false)
    private Short pornumberl;

    @Column(name = "PORPREFIXD", nullable = false, length = 6)
    private String porprefixd;

    @Column(name = "PORBODYD", nullable = false, length = 22)
    private String porbodyd;

    @Column(name = "RCPNUMBERL", nullable = false)
    private Short rcpnumberl;

    @Column(name = "RCPPREFIXD", nullable = false, length = 6)
    private String rcpprefixd;

    @Column(name = "RCPBODYD", nullable = false, length = 22)
    private String rcpbodyd;

    @Column(name = "RETNUMBERL", nullable = false)
    private Short retnumberl;

    @Column(name = "RETPREFIXD", nullable = false, length = 6)
    private String retprefixd;

    @Column(name = "RETBODYD", nullable = false, length = 22)
    private String retbodyd;

    @Column(name = "DEFTEMP", nullable = false, length = 6)
    private String deftemp;

    @Column(name = "LGENDAYEND", nullable = false, precision = 19)
    private BigDecimal lgendayend;

    @Column(name = "APPENDGL", nullable = false)
    private Short appendgl;

    @Column(name = "CONSOLGL", nullable = false)
    private Short consolgl;

    @Column(name = "DEFERGL", nullable = false)
    private Short defergl;

    @Column(name = "REFERENCGL", nullable = false)
    private Short referencgl;

    @Column(name = "DESCRIPTGL", nullable = false)
    private Short descriptgl;

    @Column(name = "GLACEXPENS", nullable = false, length = 45)
    private String glacexpens;

    @Column(name = "GLCSTACCT", nullable = false, length = 45)
    private String glcstacct;

    @Column(name = "DEFCOST", nullable = false)
    private Short defcost;

    @Column(name = "NIPAYBACCT", nullable = false, length = 45)
    private String nipaybacct;

    @Column(name = "NONINVTOGL", nullable = false)
    private Short noninvtogl;

    @Column(name = "EAPAYBACCT", nullable = false, length = 45)
    private String eapaybacct;

    @Column(name = "EXPADDTOGL", nullable = false)
    private Short expaddtogl;

    @Column(name = "DEFERAPPST", nullable = false)
    private Short deferappst;

    @Column(name = "SRCTYPEAD", nullable = false, length = 2)
    private String srctypead;

    @Column(name = "SRCTYPECO", nullable = false, length = 2)
    private String srctypeco;

    @Column(name = "SRCTYPECR", nullable = false, length = 2)
    private String srctypecr;

    @Column(name = "SRCTYPEDB", nullable = false, length = 2)
    private String srctypedb;

    @Column(name = "SRCTYPEIN", nullable = false, length = 2)
    private String srctypein;

    @Column(name = "SRCTYPERA", nullable = false, length = 2)
    private String srctypera;

    @Column(name = "SRCTYPERC", nullable = false, length = 2)
    private String srctyperc;

    @Column(name = "SRCTYPERJ", nullable = false, length = 2)
    private String srctyperj;

    @Column(name = "SRCTYPERT", nullable = false, length = 2)
    private String srctypert;

    @Column(name = "ALLOWNXVD", nullable = false)
    private Short allownxvd;

    @Column(name = "WARNNOITEM", nullable = false)
    private Short warnnoitem;

    @Column(name = "DATEBUSDFT", nullable = false)
    private Short datebusdft;

    @Column(name = "POSTRECENT", nullable = false)
    private Short postrecent;

    @Column(name = "CPCOSTTOPO", nullable = false)
    private Short cpcosttopo;

    @Column(name = "RQNMANAPPR", nullable = false)
    private Short rqnmanappr;

    @Column(name = "RCPNEGINV", nullable = false)
    private Short rcpneginv;

}