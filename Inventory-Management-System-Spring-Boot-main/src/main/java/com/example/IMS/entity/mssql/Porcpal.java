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
@Table(name = "PORCPAL", schema = "dbo")
public class Porcpal {
    @EmbeddedId
    private PorcpalId id;

    public Porcpal(PorcpalId id, BigDecimal audtdate, BigDecimal audttime, String audtuser, String audtorg, Short transtype, String oeonumber, Short itemexists, String itemno, String location, String itemdesc, String cntlacct, Short taxclass1, Short taxclass2, Short taxclass3, Short taxclass4, Short taxclass5, BigDecimal rqreceived, BigDecimal rqcanceled, String rcpunit, BigDecimal conversion, BigDecimal costconv, BigDecimal sqreceived, String stockunit, String costunit, BigDecimal unitcost, BigDecimal prunitcost, BigDecimal loadedcost, BigDecimal recentcost, BigDecimal fcextended, BigDecimal scextended, BigDecimal fcbaseallo, BigDecimal scbaseallo, BigDecimal fctaxallo, BigDecimal sctaxallo, BigDecimal fcprorated, BigDecimal scprorated, BigDecimal fctaxincl, BigDecimal sctaxincl, BigDecimal fctaxexcl, BigDecimal sctaxexcl, BigDecimal fcmprorate, BigDecimal scmprorate, BigDecimal taxbase1, BigDecimal taxbase2, BigDecimal taxbase3, BigDecimal taxbase4, BigDecimal taxbase5, Short taxinclud1, Short taxinclud2, Short taxinclud3, Short taxinclud4, Short taxinclud5, BigDecimal taxamount1, BigDecimal taxamount2, BigDecimal taxamount3, BigDecimal taxamount4, BigDecimal taxamount5, BigDecimal txrecvamt1, BigDecimal txrecvamt2, BigDecimal txrecvamt3, BigDecimal txrecvamt4, BigDecimal txrecvamt5, BigDecimal txexpsamt1, BigDecimal txexpsamt2, BigDecimal txexpsamt3, BigDecimal txexpsamt4, BigDecimal txexpsamt5, String glclearing, String glitem, Short glisposted, BigDecimal rqrectotal, BigDecimal sqrectotal, BigDecimal fcexttotal, BigDecimal scexttotal, Short rcpdays, BigDecimal lastcost, Short stockitem, String ponumber, Short qivalinstk, BigDecimal discpct, BigDecimal fcdiscount, BigDecimal scdiscount, BigDecimal fcdisctot, BigDecimal scdisctot, Integer values, BigDecimal rcplseq, BigDecimal fctaxrecv, BigDecimal sctaxrecv, BigDecimal fctaxexps, BigDecimal sctaxexps, BigDecimal fctaxamt1, BigDecimal fctaxamt2, BigDecimal fctaxamt3, BigDecimal fctaxamt4, BigDecimal fctaxamt5, String comment, String contract, String project, String ccategory, Short costclass, Short billtype, BigDecimal billrate, String billcurr, String aritemno, String arunit, BigDecimal rtgpercent, Short rtgdays, BigDecimal scrtgamt, BigDecimal scrtgamtot, Short rtgamtover, String gloverhead, String gllabor, BigDecimal fcovrhdamt, BigDecimal scovrhdamt, BigDecimal fclaboramt, BigDecimal sclaboramt, BigDecimal dfcunitcst, BigDecimal dscunitcst, BigDecimal dbillrate, Integer pmtransnum, BigDecimal rctaxallo, BigDecimal rctaxrecv, BigDecimal rctaxexps, BigDecimal rctaxincl, BigDecimal taramount1, BigDecimal taramount2, BigDecimal taramount3, BigDecimal taramount4, BigDecimal taramount5, BigDecimal scraxallo, BigDecimal fcraxallo, BigDecimal scraxexps, BigDecimal fcraxexps, BigDecimal defextwght, BigDecimal totdefexwt, BigDecimal dqreceived, BigDecimal deltaconve, String deltaunit, Short detailnum) {
        this.id = id;
        this.audtdate = audtdate;
        this.audttime = audttime;
        this.audtuser = audtuser;
        this.audtorg = audtorg;
        this.transtype = transtype;
        this.oeonumber = oeonumber;
        this.itemexists = itemexists;
        this.itemno = itemno;
        this.location = location;
        this.itemdesc = itemdesc;
        this.cntlacct = cntlacct;
        this.taxclass1 = taxclass1;
        this.taxclass2 = taxclass2;
        this.taxclass3 = taxclass3;
        this.taxclass4 = taxclass4;
        this.taxclass5 = taxclass5;
        this.rqreceived = rqreceived;
        this.rqcanceled = rqcanceled;
        this.rcpunit = rcpunit;
        this.conversion = conversion;
        this.costconv = costconv;
        this.sqreceived = sqreceived;
        this.stockunit = stockunit;
        this.costunit = costunit;
        this.unitcost = unitcost;
        this.prunitcost = prunitcost;
        this.loadedcost = loadedcost;
        this.recentcost = recentcost;
        this.fcextended = fcextended;
        this.scextended = scextended;
        this.fcbaseallo = fcbaseallo;
        this.scbaseallo = scbaseallo;
        this.fctaxallo = fctaxallo;
        this.sctaxallo = sctaxallo;
        this.fcprorated = fcprorated;
        this.scprorated = scprorated;
        this.fctaxincl = fctaxincl;
        this.sctaxincl = sctaxincl;
        this.fctaxexcl = fctaxexcl;
        this.sctaxexcl = sctaxexcl;
        this.fcmprorate = fcmprorate;
        this.scmprorate = scmprorate;
        this.taxbase1 = taxbase1;
        this.taxbase2 = taxbase2;
        this.taxbase3 = taxbase3;
        this.taxbase4 = taxbase4;
        this.taxbase5 = taxbase5;
        this.taxinclud1 = taxinclud1;
        this.taxinclud2 = taxinclud2;
        this.taxinclud3 = taxinclud3;
        this.taxinclud4 = taxinclud4;
        this.taxinclud5 = taxinclud5;
        this.taxamount1 = taxamount1;
        this.taxamount2 = taxamount2;
        this.taxamount3 = taxamount3;
        this.taxamount4 = taxamount4;
        this.taxamount5 = taxamount5;
        this.txrecvamt1 = txrecvamt1;
        this.txrecvamt2 = txrecvamt2;
        this.txrecvamt3 = txrecvamt3;
        this.txrecvamt4 = txrecvamt4;
        this.txrecvamt5 = txrecvamt5;
        this.txexpsamt1 = txexpsamt1;
        this.txexpsamt2 = txexpsamt2;
        this.txexpsamt3 = txexpsamt3;
        this.txexpsamt4 = txexpsamt4;
        this.txexpsamt5 = txexpsamt5;
        this.glclearing = glclearing;
        this.glitem = glitem;
        this.glisposted = glisposted;
        this.rqrectotal = rqrectotal;
        this.sqrectotal = sqrectotal;
        this.fcexttotal = fcexttotal;
        this.scexttotal = scexttotal;
        this.rcpdays = rcpdays;
        this.lastcost = lastcost;
        this.stockitem = stockitem;
        this.ponumber = ponumber;
        this.qivalinstk = qivalinstk;
        this.discpct = discpct;
        this.fcdiscount = fcdiscount;
        this.scdiscount = scdiscount;
        this.fcdisctot = fcdisctot;
        this.scdisctot = scdisctot;
        this.values = values;
        this.rcplseq = rcplseq;
        this.fctaxrecv = fctaxrecv;
        this.sctaxrecv = sctaxrecv;
        this.fctaxexps = fctaxexps;
        this.sctaxexps = sctaxexps;
        this.fctaxamt1 = fctaxamt1;
        this.fctaxamt2 = fctaxamt2;
        this.fctaxamt3 = fctaxamt3;
        this.fctaxamt4 = fctaxamt4;
        this.fctaxamt5 = fctaxamt5;
        this.comment = comment;
        this.contract = contract;
        this.project = project;
        this.ccategory = ccategory;
        this.costclass = costclass;
        this.billtype = billtype;
        this.billrate = billrate;
        this.billcurr = billcurr;
        this.aritemno = aritemno;
        this.arunit = arunit;
        this.rtgpercent = rtgpercent;
        this.rtgdays = rtgdays;
        this.scrtgamt = scrtgamt;
        this.scrtgamtot = scrtgamtot;
        this.rtgamtover = rtgamtover;
        this.gloverhead = gloverhead;
        this.gllabor = gllabor;
        this.fcovrhdamt = fcovrhdamt;
        this.scovrhdamt = scovrhdamt;
        this.fclaboramt = fclaboramt;
        this.sclaboramt = sclaboramt;
        this.dfcunitcst = dfcunitcst;
        this.dscunitcst = dscunitcst;
        this.dbillrate = dbillrate;
        this.pmtransnum = pmtransnum;
        this.rctaxallo = rctaxallo;
        this.rctaxrecv = rctaxrecv;
        this.rctaxexps = rctaxexps;
        this.rctaxincl = rctaxincl;
        this.taramount1 = taramount1;
        this.taramount2 = taramount2;
        this.taramount3 = taramount3;
        this.taramount4 = taramount4;
        this.taramount5 = taramount5;
        this.scraxallo = scraxallo;
        this.fcraxallo = fcraxallo;
        this.scraxexps = scraxexps;
        this.fcraxexps = fcraxexps;
        this.defextwght = defextwght;
        this.totdefexwt = totdefexwt;
        this.dqreceived = dqreceived;
        this.deltaconve = deltaconve;
        this.deltaunit = deltaunit;
        this.detailnum = detailnum;
    }

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

    @Column(name = "RQCANCELED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqcanceled;

    @Column(name = "RCPUNIT", nullable = false, length = 10)
    private String rcpunit;

    @Column(name = "CONVERSION", nullable = false, precision = 19, scale = 6)
    private BigDecimal conversion;

    @Column(name = "COSTCONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal costconv;

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

    @Column(name = "RECENTCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal recentcost;

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

    @Column(name = "FCMPRORATE", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcmprorate;

    @Column(name = "SCMPRORATE", nullable = false, precision = 19, scale = 3)
    private BigDecimal scmprorate;

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

    @Column(name = "RCPDAYS", nullable = false)
    private Short rcpdays;

    @Column(name = "LASTCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal lastcost;

    @Column(name = "STOCKITEM", nullable = false)
    private Short stockitem;

    @Column(name = "PONUMBER", nullable = false, length = 22)
    private String ponumber;

    @Column(name = "QIVALINSTK", nullable = false)
    private Short qivalinstk;

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

    @Column(name = "RCPLSEQ", nullable = false, precision = 19)
    private BigDecimal rcplseq;

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

    @Column(name = "SCRTGAMTOT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scrtgamtot;

    @Column(name = "RTGAMTOVER", nullable = false)
    private Short rtgamtover;

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

    @Column(name = "DEFEXTWGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal defextwght;

    @Column(name = "TOTDEFEXWT", nullable = false, precision = 19, scale = 4)
    private BigDecimal totdefexwt;

    @Column(name = "DQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal dqreceived;

    @Column(name = "DELTACONVE", nullable = false, precision = 19, scale = 6)
    private BigDecimal deltaconve;

    @Column(name = "DELTAUNIT", nullable = false, length = 10)
    private String deltaunit;


    @Column(name = "DETAILNUM", nullable = false)
    private Short detailnum;

}