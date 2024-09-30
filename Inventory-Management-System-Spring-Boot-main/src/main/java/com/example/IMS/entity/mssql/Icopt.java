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
@Table(name = "ICOPT")
public class Icopt {
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

    @Column(name = "CONTACT", nullable = false, length = 60)
    private String contact;

    @Column(name = "PHONE", nullable = false, length = 30)
    private String phone;

    @Column(name = "FAX", nullable = false, length = 30)
    private String fax;

    @Column(name = "FRACTQTY", nullable = false)
    private Short fractqty;

    @Column(name = "NEGQTY", nullable = false)
    private Short negqty;

    @Column(name = "TRANSHIST", nullable = false)
    private Short transhist;

    @Column(name = "JOBCOST", nullable = false)
    private Short jobcost;

    @Column(name = "REFCHOICE", nullable = false)
    private Short refchoice;

    @Column(name = "DESCCHOICE", nullable = false)
    private Short descchoice;

    @Column(name = "WEIGHTUNIT", nullable = false, length = 10)
    private String weightunit;

    @Column(name = "COST1NAME", nullable = false, length = 10)
    private String cost1name;

    @Column(name = "COST2NAME", nullable = false, length = 10)
    private String cost2name;

    @Column(name = "DAYEND", nullable = false)
    private Short dayend;

    @Column(name = "TRANSNUM", nullable = false, precision = 19)
    private BigDecimal transnum;

    @Column(name = "NXTALTSET", nullable = false)
    private Integer nxtaltset;

    @Column(name = "GLDAYEND", nullable = false)
    private Integer gldayend;

    @Column(name = "ADJENSEQ", nullable = false)
    private Integer adjenseq;

    @Column(name = "ASSMENSEQ", nullable = false)
    private Integer assmenseq;

    @Column(name = "RECENSEQ", nullable = false)
    private Integer recenseq;

    @Column(name = "SHIPENSEQ", nullable = false)
    private Integer shipenseq;

    @Column(name = "TRANFENSEQ", nullable = false)
    private Integer tranfenseq;

    @Column(name = "HISTENSEQ", nullable = false)
    private Integer histenseq;

    @Column(name = "DAYENDSEQ", nullable = false)
    private Integer dayendseq;

    @Column(name = "MULTICURR", nullable = false)
    private Short multicurr;

    @Column(name = "DEFERGLPST", nullable = false)
    private Short deferglpst;

    @Column(name = "APPENDGL", nullable = false)
    private Short appendgl;

    @Column(name = "CONSOLGL", nullable = false)
    private Short consolgl;

    @Column(name = "STATCALNDR", nullable = false)
    private Short statcalndr;

    @Column(name = "STATPRD", nullable = false)
    private Short statprd;

    @Column(name = "STATEDIT", nullable = false)
    private Short statedit;

    @Column(name = "CRTITEMLOC", nullable = false)
    private Short crtitemloc;

    @Column(name = "ADDCSTTYPE", nullable = false)
    private Short addcsttype;

    @Column(name = "RATETYPE", nullable = false, length = 2)
    private String ratetype;

    @Column(name = "ITEMBRKID", nullable = false, length = 6)
    private String itembrkid;

    @Column(name = "STATACCUM", nullable = false)
    private Short stataccum;

    @Column(name = "PSTTOCLOSD", nullable = false)
    private Short psttoclosd;

    @Column(name = "HYHEN", nullable = false)
    private Short hyhen;

    @Column(name = "FWDSLASH", nullable = false)
    private Short fwdslash;

    @Column(name = "BCKSLASH", nullable = false)
    private Short bckslash;

    @Column(name = "ASTERISK", nullable = false)
    private Short asterisk;

    @Column(name = "PERIOD", nullable = false)
    private Short period;

    @Column(name = "LFTPARENS", nullable = false)
    private Short lftparens;

    @Column(name = "RGTPARENS", nullable = false)
    private Short rgtparens;

    @Column(name = "POUNDSGN", nullable = false)
    private Short poundsgn;

    @Column(name = "RECNONSTK", nullable = false)
    private Short recnonstk;

    @Column(name = "DELPROMPT", nullable = false)
    private Short delprompt;

    @Column(name = "COSTDURING", nullable = false)
    private Short costduring;

    @Column(name = "ASSNUMBERL", nullable = false)
    private Short assnumberl;

    @Column(name = "ASSPREFIXD", nullable = false, length = 6)
    private String assprefixd;

    @Column(name = "ASSBODYD", nullable = false, length = 22)
    private String assbodyd;

    @Column(name = "DASNUMBERL", nullable = false)
    private Short dasnumberl;

    @Column(name = "DASPREFIXD", nullable = false, length = 6)
    private String dasprefixd;

    @Column(name = "DASBODYD", nullable = false, length = 22)
    private String dasbodyd;

    @Column(name = "TRFNUMBERL", nullable = false)
    private Short trfnumberl;

    @Column(name = "TRFPREFIXD", nullable = false, length = 6)
    private String trfprefixd;

    @Column(name = "TRFBODYD", nullable = false, length = 22)
    private String trfbodyd;

    @Column(name = "ADJNUMBERL", nullable = false)
    private Short adjnumberl;

    @Column(name = "ADJPREFIXD", nullable = false, length = 6)
    private String adjprefixd;

    @Column(name = "ADJBODYD", nullable = false, length = 22)
    private String adjbodyd;

    @Column(name = "SHPNUMBERL", nullable = false)
    private Short shpnumberl;

    @Column(name = "SHPPREFIXD", nullable = false, length = 6)
    private String shpprefixd;

    @Column(name = "SHPBODYD", nullable = false, length = 22)
    private String shpbodyd;

    @Column(name = "SRTNUMBERL", nullable = false)
    private Short srtnumberl;

    @Column(name = "SRTPREFIXD", nullable = false, length = 6)
    private String srtprefixd;

    @Column(name = "SRTBODYD", nullable = false, length = 22)
    private String srtbodyd;

    @Column(name = "RCPNUMBERL", nullable = false)
    private Short rcpnumberl;

    @Column(name = "RCPPREFIXD", nullable = false, length = 6)
    private String rcpprefixd;

    @Column(name = "RCPBODYD", nullable = false, length = 22)
    private String rcpbodyd;

    @Column(name = "TRCNUMBERL", nullable = false)
    private Short trcnumberl;

    @Column(name = "TRCPREFIXD", nullable = false, length = 6)
    private String trcprefixd;

    @Column(name = "TRCBODYD", nullable = false, length = 22)
    private String trcbodyd;

    @Column(name = "RECDESEQ", nullable = false)
    private Integer recdeseq;

    @Column(name = "GITLOC", nullable = false, length = 6)
    private String gitloc;

    @Column(name = "DEFUOM", nullable = false)
    private Short defuom;

    @Column(name = "AGING1", nullable = false, precision = 5)
    private BigDecimal aging1;

    @Column(name = "AGING2", nullable = false, precision = 5)
    private BigDecimal aging2;

    @Column(name = "AGING3", nullable = false, precision = 5)
    private BigDecimal aging3;

    @Column(name = "SLAUDURING", nullable = false)
    private Short slauduring;

    @Column(name = "ICSENSEQ", nullable = false)
    private Integer icsenseq;

    @Column(name = "ICSNUMBERL", nullable = false)
    private Short icsnumberl;

    @Column(name = "ICSPREFIXD", nullable = false, length = 6)
    private String icsprefixd;

    @Column(name = "ICSBODYD", nullable = false, length = 22)
    private String icsbodyd;

    @Column(name = "SRCTYPERC", nullable = false, length = 2)
    private String srctyperc;

    @Column(name = "SRCTYPERR", nullable = false, length = 2)
    private String srctyperr;

    @Column(name = "SRCTYPERA", nullable = false, length = 2)
    private String srctypera;

    @Column(name = "SRCTYPESH", nullable = false, length = 2)
    private String srctypesh;

    @Column(name = "SRCTYPESR", nullable = false, length = 2)
    private String srctypesr;

    @Column(name = "SRCTYPETF", nullable = false, length = 2)
    private String srctypetf;

    @Column(name = "SRCTYPEAS", nullable = false, length = 2)
    private String srctypeas;

    @Column(name = "SRCTYPEAD", nullable = false, length = 2)
    private String srctypead;

    @Column(name = "SRCTYPECO", nullable = false, length = 2)
    private String srctypeco;

    @Column(name = "SRCTYPEDA", nullable = false, length = 2)
    private String srctypeda;

    @Column(name = "SRCTYPEIN", nullable = false, length = 2)
    private String srctypein;

    @Column(name = "DATEBUSDFT", nullable = false)
    private Short datebusdft;

    @Column(name = "SERIALMASK", nullable = false, length = 6)
    private String serialmask;

    @Column(name = "SUSEEXPDAY", nullable = false)
    private Short suseexpday;

    @Column(name = "SEXPDAYS", nullable = false)
    private Short sexpdays;

    @Column(name = "SDIFQTYOK", nullable = false)
    private Short sdifqtyok;

    @Column(name = "SALCQTYORD", nullable = false)
    private Short salcqtyord;

    @Column(name = "SSORTBY", nullable = false)
    private Short ssortby;

    @Column(name = "SFIRST", nullable = false)
    private Short sfirst;

    @Column(name = "SEXPLEVEL", nullable = false)
    private Short sexplevel;

    @Column(name = "SHYPHEN", nullable = false)
    private Short shyphen;

    @Column(name = "SFWDSLASH", nullable = false)
    private Short sfwdslash;

    @Column(name = "SBCKSLASH", nullable = false)
    private Short sbckslash;

    @Column(name = "SASTERISK", nullable = false)
    private Short sasterisk;

    @Column(name = "SPERIOD", nullable = false)
    private Short speriod;

    @Column(name = "SLFPARENS", nullable = false)
    private Short slfparens;

    @Column(name = "SRGTPARENS", nullable = false)
    private Short srgtparens;

    @Column(name = "SPOUNDSIGN", nullable = false)
    private Short spoundsign;

    @Column(name = "SLFBRACKT", nullable = false)
    private Short slfbrackt;

    @Column(name = "SRGTBRACKT", nullable = false)
    private Short srgtbrackt;

    @Column(name = "SLFBRACE", nullable = false)
    private Short slfbrace;

    @Column(name = "SRGTBRACE", nullable = false)
    private Short srgtbrace;

    @Column(name = "LOTMASK", nullable = false, length = 6)
    private String lotmask;

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

    @Column(name = "LALCQTYORD", nullable = false)
    private Short lalcqtyord;

    @Column(name = "LSORTBY", nullable = false)
    private Short lsortby;

    @Column(name = "LFIRST", nullable = false)
    private Short lfirst;

    @Column(name = "LEXPLEVEL", nullable = false)
    private Short lexplevel;

    @Column(name = "LHYPHEN", nullable = false)
    private Short lhyphen;

    @Column(name = "LFWDSLASH", nullable = false)
    private Short lfwdslash;

    @Column(name = "LBCKSLASH", nullable = false)
    private Short lbckslash;

    @Column(name = "LASTERISK", nullable = false)
    private Short lasterisk;

    @Column(name = "LPERIOD", nullable = false)
    private Short lperiod;

    @Column(name = "LLFPARENS", nullable = false)
    private Short llfparens;

    @Column(name = "LRGTPARENS", nullable = false)
    private Short lrgtparens;

    @Column(name = "LPOUNDSIGN", nullable = false)
    private Short lpoundsign;

    @Column(name = "LLFBRACKT", nullable = false)
    private Short llfbrackt;

    @Column(name = "LRGTBRACKT", nullable = false)
    private Short lrgtbrackt;

    @Column(name = "LLFBRACE", nullable = false)
    private Short llfbrace;

    @Column(name = "LRGTBRACE", nullable = false)
    private Short lrgtbrace;

    @Column(name = "RECALENSEQ", nullable = false, precision = 19)
    private BigDecimal recalenseq;

    @Column(name = "RECNUMBERL", nullable = false)
    private Short recnumberl;

    @Column(name = "RECPREFIXD", nullable = false, length = 6)
    private String recprefixd;

    @Column(name = "RECBODYD", nullable = false, length = 22)
    private String recbodyd;

    @Column(name = "RELNUMBERL", nullable = false)
    private Short relnumberl;

    @Column(name = "RELPREFIXD", nullable = false, length = 6)
    private String relprefixd;

    @Column(name = "RELBODYD", nullable = false, length = 22)
    private String relbodyd;

    @Column(name = "COMENSEQ", nullable = false, precision = 19)
    private BigDecimal comenseq;

    @Column(name = "COMNUMBERL", nullable = false)
    private Short comnumberl;

    @Column(name = "COMPREFIXD", nullable = false, length = 6)
    private String comprefixd;

    @Column(name = "COMBODYD", nullable = false, length = 22)
    private String combodyd;

    @Column(name = "SPLNUMBERL", nullable = false)
    private Short splnumberl;

    @Column(name = "SPLPREFIXD", nullable = false, length = 6)
    private String splprefixd;

    @Column(name = "SPLBODYD", nullable = false, length = 22)
    private String splbodyd;

    @Column(name = "RCNENSEQ", nullable = false, precision = 19)
    private BigDecimal rcnenseq;

    @Column(name = "RCNNUMBERL", nullable = false)
    private Short rcnnumberl;

    @Column(name = "RCNPREFIXD", nullable = false, length = 6)
    private String rcnprefixd;

    @Column(name = "RCNBODYD", nullable = false, length = 22)
    private String rcnbodyd;

    @Column(name = "DUPSERIALS", nullable = false)
    private Short dupserials;

    @Column(name = "SALESPERDS", nullable = false)
    private Short salesperds;

    @Column(name = "UPDTMINREQ", nullable = false)
    private Short updtminreq;

    @Column(name = "MINFACTOR", nullable = false, precision = 9, scale = 2)
    private BigDecimal minfactor;

    @Column(name = "MAXFACTOR", nullable = false, precision = 9, scale = 2)
    private BigDecimal maxfactor;

    @Column(name = "SALESHL", nullable = false, precision = 9, scale = 2)
    private BigDecimal saleshl;

    @Column(name = "TRENDHL", nullable = false, precision = 9, scale = 2)
    private BigDecimal trendhl;

    @Column(name = "MINMAXHL", nullable = false, precision = 9, scale = 2)
    private BigDecimal minmaxhl;

    @Column(name = "CMNTDAYS", nullable = false, precision = 5)
    private BigDecimal cmntdays;

    @Column(name = "FLUPDAYS", nullable = false, precision = 5)
    private BigDecimal flupdays;

    @Column(name = "CMNTTYPE", nullable = false, length = 8)
    private String cmnttype;

    @Column(name = "SWCMNTTYPE", nullable = false)
    private Short swcmnttype;

    @Column(name = "DLASTDEP", nullable = false, precision = 9)
    private BigDecimal dlastdep;

    @Column(name = "TLASTDEP", nullable = false, precision = 9)
    private BigDecimal tlastdep;

}