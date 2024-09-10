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
@Table(name = "POINVAL")
public class Poinval {
    @EmbeddedId
    private PoinvalId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "TRANSTYPE", nullable = false)
    private Short transtype;

    @Column(name = "OEONUMBER", nullable = false, length = 22)
    private String oeonumber;

    @Column(name = "ITEMEXISTS", nullable = false)
    private Short itemexists;

    @Column(name = "ITEMNO", nullable = false, length = 24)
    private String itemno;

    @Column(name = "LOCATION", nullable = false, length = 6)
    private String location;

    @Column(name = "ITEMDESC", nullable = false, length = 60)
    private String itemdesc;

    @Column(name = "CNTLACCT", nullable = false, length = 6)
    private String cntlacct;

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

    @Column(name = "RQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqreceived;

    @Column(name = "RCPUNIT", nullable = false, length = 10)
    private String rcpunit;

    @Column(name = "CONVERSION", nullable = false, precision = 19, scale = 6)
    private BigDecimal conversion;

    @Column(name = "SQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqreceived;

    @Column(name = "STOCKUNIT", nullable = false, length = 10)
    private String stockunit;

    @Column(name = "COSTUNIT", nullable = false, length = 10)
    private String costunit;

    @Column(name = "UNITCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal unitcost;

    @Column(name = "PRUNITCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal prunitcost;

    @Column(name = "LOADEDCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal loadedcost;

    @Column(name = "FCEXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcextended;

    @Column(name = "SCEXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal scextended;

    @Column(name = "FCBASEALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcbaseallo;

    @Column(name = "SCBASEALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal scbaseallo;

    @Column(name = "FCTAXALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxallo;

    @Column(name = "SCTAXALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal sctaxallo;

    @Column(name = "FCPRORATED", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcprorated;

    @Column(name = "SCPRORATED", nullable = false, precision = 19, scale = 3)
    private BigDecimal scprorated;

    @Column(name = "FCTAXINCL", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxincl;

    @Column(name = "SCTAXINCL", nullable = false, precision = 19, scale = 3)
    private BigDecimal sctaxincl;

    @Column(name = "FCTAXEXCL", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxexcl;

    @Column(name = "SCTAXEXCL", nullable = false, precision = 19, scale = 3)
    private BigDecimal sctaxexcl;

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

    @Column(name = "TAXINCLUD1", nullable = false)
    private Short taxinclud1;

    @Column(name = "TAXINCLUD2", nullable = false)
    private Short taxinclud2;

    @Column(name = "TAXINCLUD3", nullable = false)
    private Short taxinclud3;

    @Column(name = "TAXINCLUD4", nullable = false)
    private Short taxinclud4;

    @Column(name = "TAXINCLUD5", nullable = false)
    private Short taxinclud5;

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

    @Column(name = "GLCLEARING", nullable = false, length = 45)
    private String glclearing;

    @Column(name = "GLITEM", nullable = false, length = 45)
    private String glitem;

    @Column(name = "GLISPOSTED", nullable = false)
    private Short glisposted;

    @Column(name = "RQRECTOTAL", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqrectotal;

    @Column(name = "SQRECTOTAL", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqrectotal;

    @Column(name = "FCEXTTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcexttotal;

    @Column(name = "SCEXTTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal scexttotal;

    @Column(name = "STOCKITEM", nullable = false)
    private Short stockitem;

    @Column(name = "PONUMBER", nullable = false, length = 22)
    private String ponumber;

    @Column(name = "RCPNUMBER", nullable = false, length = 22)
    private String rcpnumber;

    @Column(name = "DISCPCT", nullable = false, precision = 9, scale = 5)
    private BigDecimal discpct;

    @Column(name = "FCDISCOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcdiscount;

    @Column(name = "SCDISCOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scdiscount;

    @Column(name = "FCDISCTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcdisctot;

    @Column(name = "SCDISCTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scdisctot;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "TERMDISCBL", nullable = false)
    private Short termdiscbl;

    @Column(name = "RCPLSEQ", nullable = false, precision = 19)
    private BigDecimal rcplseq;

    @Column(name = "INVLSEQ", nullable = false, precision = 19)
    private BigDecimal invlseq;

    @Column(name = "FCTAXRECV", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxrecv;

    @Column(name = "SCTAXRECV", nullable = false, precision = 19, scale = 3)
    private BigDecimal sctaxrecv;

    @Column(name = "FCTAXEXPS", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxexps;

    @Column(name = "SCTAXEXPS", nullable = false, precision = 19, scale = 3)
    private BigDecimal sctaxexps;

    @Column(name = "FCTAXAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxamt1;

    @Column(name = "FCTAXAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxamt2;

    @Column(name = "FCTAXAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxamt3;

    @Column(name = "FCTAXAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxamt4;

    @Column(name = "FCTAXAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal fctaxamt5;

    @Column(name = "COMMENT", nullable = false, length = 250)
    private String comment;

    @Column(name = "CONTRACT", nullable = false, length = 16)
    private String contract;

    @Column(name = "PROJECT", nullable = false, length = 16)
    private String project;

    @Column(name = "CCATEGORY", nullable = false, length = 16)
    private String ccategory;

    @Column(name = "COSTCLASS", nullable = false)
    private Short costclass;

    @Column(name = "BILLTYPE", nullable = false)
    private Short billtype;

    @Column(name = "BILLRATE", nullable = false, precision = 19, scale = 6)
    private BigDecimal billrate;

    @Column(name = "BILLCURR", nullable = false, length = 3)
    private String billcurr;

    @Column(name = "ARITEMNO", nullable = false, length = 16)
    private String aritemno;

    @Column(name = "ARUNIT", nullable = false, length = 10)
    private String arunit;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "RTGDAYS", nullable = false)
    private Short rtgdays;

    @Column(name = "SCRTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scrtgamt;

    @Column(name = "FCRTGAMTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcrtgamtot;

    @Column(name = "SCRTGAMTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scrtgamtot;

    @Column(name = "RTGDATEDUE", nullable = false, precision = 9)
    private BigDecimal rtgdatedue;

    @Column(name = "RTGAMTOVER", nullable = false)
    private Short rtgamtover;

    @Column(name = "RTGDDTOVER", nullable = false)
    private Short rtgddtover;

    @Column(name = "GLOVERHEAD", nullable = false, length = 45)
    private String gloverhead;

    @Column(name = "GLLABOR", nullable = false, length = 45)
    private String gllabor;

    @Column(name = "FCOVRHDAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcovrhdamt;

    @Column(name = "SCOVRHDAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scovrhdamt;

    @Column(name = "FCLABORAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fclaboramt;

    @Column(name = "SCLABORAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal sclaboramt;

    @Column(name = "DFCUNITCST", nullable = false, precision = 19, scale = 6)
    private BigDecimal dfcunitcst;

    @Column(name = "DSCUNITCST", nullable = false, precision = 19, scale = 6)
    private BigDecimal dscunitcst;

    @Column(name = "DBILLRATE", nullable = false, precision = 19, scale = 6)
    private BigDecimal dbillrate;

    @Column(name = "PMTRANSNUM", nullable = false)
    private Integer pmtransnum;

    @Column(name = "RCTAXALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal rctaxallo;

    @Column(name = "RCTAXRECV", nullable = false, precision = 19, scale = 3)
    private BigDecimal rctaxrecv;

    @Column(name = "RCTAXEXPS", nullable = false, precision = 19, scale = 3)
    private BigDecimal rctaxexps;

    @Column(name = "RCTAXINCL", nullable = false, precision = 19, scale = 3)
    private BigDecimal rctaxincl;

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

    @Column(name = "SCRAXALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal scraxallo;

    @Column(name = "FCRAXALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcraxallo;

    @Column(name = "SCRAXEXPS", nullable = false, precision = 19, scale = 3)
    private BigDecimal scraxexps;

    @Column(name = "FCRAXEXPS", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcraxexps;

    @Column(name = "RFAPALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapallo;

    @Column(name = "RFAPEXPS", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapexps;

    @Column(name = "RFAPAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapamt1;

    @Column(name = "RFAPAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapamt2;

    @Column(name = "RFAPAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapamt3;

    @Column(name = "RFAPAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapamt4;

    @Column(name = "RFAPAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal rfapamt5;

    @Column(name = "DEFEXTWGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal defextwght;

    @Column(name = "TOTDEFEXWT", nullable = false, precision = 19, scale = 4)
    private BigDecimal totdefexwt;

    @Column(name = "DETAILNUM", nullable = false)
    private Short detailnum;

}