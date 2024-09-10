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
@Table(name = "APVEN", schema = "dbo")
public class Apven {
    @Id
    @Column(name = "VENDORID", nullable = false, length = 12)
    private String vendorid;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "SHORTNAME", nullable = false, length = 10)
    private String shortname;

    @Column(name = "IDGRP", nullable = false, length = 6)
    private String idgrp;

    @Column(name = "SWACTV", nullable = false)
    private Short swactv;

    @Column(name = "DATEINAC", nullable = false, precision = 9)
    private BigDecimal dateinac;

    @Column(name = "DATELASTMN", nullable = false, precision = 9)
    private BigDecimal datelastmn;

    @Column(name = "SWHOLD", nullable = false)
    private Short swhold;

    @Column(name = "DATESTART", nullable = false, precision = 9)
    private BigDecimal datestart;

    @Column(name = "IDPPNT", nullable = false, length = 12)
    private String idppnt;

    @Column(name = "VENDNAME", nullable = false, length = 60)
    private String vendname;

    @Column(name = "TEXTSTRE1", nullable = false, length = 60)
    private String textstre1;

    @Column(name = "TEXTSTRE2", nullable = false, length = 60)
    private String textstre2;

    @Column(name = "TEXTSTRE3", nullable = false, length = 60)
    private String textstre3;

    @Column(name = "TEXTSTRE4", nullable = false, length = 60)
    private String textstre4;

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

    @Column(name = "TEXTPHON1", nullable = false, length = 30)
    private String textphon1;

    @Column(name = "TEXTPHON2", nullable = false, length = 30)
    private String textphon2;

    @Column(name = "PRIMRMIT", nullable = false, length = 6)
    private String primrmit;

    @Column(name = "IDACCTSET", nullable = false, length = 6)
    private String idacctset;

    @Column(name = "CURNCODE", nullable = false, length = 3)
    private String curncode;

    @Column(name = "RATETYPE", nullable = false, length = 2)
    private String ratetype;

    @Column(name = "BANKID", nullable = false, length = 8)
    private String bankid;

    @Column(name = "PRTSEPCHKS", nullable = false)
    private Short prtsepchks;

    @Column(name = "DISTSETID", nullable = false, length = 6)
    private String distsetid;

    @Column(name = "DISTCODE", nullable = false, length = 6)
    private String distcode;

    @Column(name = "GLACCNT", nullable = false, length = 45)
    private String glaccnt;

    @Column(name = "TERMSCODE", nullable = false, length = 6)
    private String termscode;

    @Column(name = "DUPINVCCD", nullable = false)
    private Short dupinvccd;

    @Column(name = "DUPAMTCODE", nullable = false)
    private Short dupamtcode;

    @Column(name = "DUPDATECD", nullable = false)
    private Short dupdatecd;

    @Column(name = "CODETAXGRP", nullable = false, length = 12)
    private String codetaxgrp;

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

    @Column(name = "TAXRPTSW", nullable = false)
    private Short taxrptsw;

    @Column(name = "SUBJTOWTHH", nullable = false)
    private Short subjtowthh;

    @Column(name = "TAXNBR", nullable = false, length = 20)
    private String taxnbr;

    @Column(name = "TAXIDTYPE", nullable = false)
    private Short taxidtype;

    @Column(name = "TAXNOTE2SW", nullable = false)
    private Short taxnote2sw;

    @Column(name = "CLASID", nullable = false, length = 6)
    private String clasid;

    @Column(name = "AMTCRLIMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtcrlimt;

    @Column(name = "AMTBALDUET", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbalduet;

    @Column(name = "AMTBALDUEH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbaldueh;

    @Column(name = "AMTPPDINVT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtppdinvt;

    @Column(name = "AMTPPDINVH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtppdinvh;

    @Column(name = "DTLASTRVAL", nullable = false, precision = 9)
    private BigDecimal dtlastrval;

    @Column(name = "AMTBALLARV", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtballarv;

    @Column(name = "CNTOPENINV", nullable = false, precision = 7)
    private BigDecimal cntopeninv;

    @Column(name = "CNTPPDINVC", nullable = false, precision = 7)
    private BigDecimal cntppdinvc;

    @Column(name = "CNTINVPAID", nullable = false, precision = 7)
    private BigDecimal cntinvpaid;

    @Column(name = "DAYSTOPAY", nullable = false, precision = 7)
    private BigDecimal daystopay;

    @Column(name = "DATEINVCHI", nullable = false, precision = 9)
    private BigDecimal dateinvchi;

    @Column(name = "DATEBALHI", nullable = false, precision = 9)
    private BigDecimal datebalhi;

    @Column(name = "DATEINVHIL", nullable = false, precision = 9)
    private BigDecimal dateinvhil;

    @Column(name = "DATEBALHIL", nullable = false, precision = 9)
    private BigDecimal datebalhil;

    @Column(name = "DATELASTAC", nullable = false, precision = 9)
    private BigDecimal datelastac;

    @Column(name = "DATELASTIV", nullable = false, precision = 9)
    private BigDecimal datelastiv;

    @Column(name = "DATELASTCR", nullable = false, precision = 9)
    private BigDecimal datelastcr;

    @Column(name = "DATELASTDR", nullable = false, precision = 9)
    private BigDecimal datelastdr;

    @Column(name = "DATELASTPA", nullable = false, precision = 9)
    private BigDecimal datelastpa;

    @Column(name = "DATELASTDI", nullable = false, precision = 9)
    private BigDecimal datelastdi;

    @Column(name = "DATELSTADJ", nullable = false, precision = 9)
    private BigDecimal datelstadj;

    @Column(name = "IDINVCHI", nullable = false, length = 22)
    private String idinvchi;

    @Column(name = "IDINVCHILY", nullable = false, length = 22)
    private String idinvchily;

    @Column(name = "AMTINVHIT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvhit;

    @Column(name = "AMTBALHIT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbalhit;

    @Column(name = "AMTWTHTCUR", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwthtcur;

    @Column(name = "AMTINVHILT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvhilt;

    @Column(name = "AMTBALHILT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbalhilt;

    @Column(name = "AMTWTHLYTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwthlytc;

    @Column(name = "AMTLASTIVT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastivt;

    @Column(name = "AMTLASTCRT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastcrt;

    @Column(name = "AMTLASTDRT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastdrt;

    @Column(name = "AMTLASTPYT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastpyt;

    @Column(name = "AMTLASTDIT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastdit;

    @Column(name = "AMTLASTADT", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastadt;

    @Column(name = "AMTINVHIH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvhih;

    @Column(name = "AMTBALHIH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbalhih;

    @Column(name = "AMTWTHHCUR", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwthhcur;

    @Column(name = "AMTINVHILH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvhilh;

    @Column(name = "AMTBALHILH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtbalhilh;

    @Column(name = "AMTWTHLYHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtwthlyhc;

    @Column(name = "AMTLASTIVH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastivh;

    @Column(name = "AMTLASTCRH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastcrh;

    @Column(name = "AMTLASTDRH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastdrh;

    @Column(name = "AMTLASTPYH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastpyh;

    @Column(name = "AMTLASTDIH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastdih;

    @Column(name = "AMTLASTADH", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtlastadh;

    @Column(name = "PAYMCODE", nullable = false, length = 12)
    private String paymcode;

    @Column(name = "IDTAXREGI1", nullable = false, length = 20)
    private String idtaxregi1;

    @Column(name = "IDTAXREGI2", nullable = false, length = 20)
    private String idtaxregi2;

    @Column(name = "IDTAXREGI3", nullable = false, length = 20)
    private String idtaxregi3;

    @Column(name = "IDTAXREGI4", nullable = false, length = 20)
    private String idtaxregi4;

    @Column(name = "IDTAXREGI5", nullable = false, length = 20)
    private String idtaxregi5;

    @Column(name = "SWDISTBY", nullable = false)
    private Short swdistby;

    @Column(name = "CODECHECK", nullable = false, length = 3)
    private String codecheck;

    @Column(name = "AVGDAYSPAY", nullable = false, precision = 9, scale = 1)
    private BigDecimal avgdayspay;

    @Column(name = "AVGPAYMENT", nullable = false, precision = 19, scale = 3)
    private BigDecimal avgpayment;

    @Column(name = "AMTINVPDHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvpdhc;

    @Column(name = "AMTINVPDTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtinvpdtc;

    @Column(name = "CNTNBRCHKS", nullable = false, precision = 7)
    private BigDecimal cntnbrchks;

    @Column(name = "SWTXINC1", nullable = false)
    private Short swtxinc1;

    @Column(name = "SWTXINC2", nullable = false)
    private Short swtxinc2;

    @Column(name = "SWTXINC3", nullable = false)
    private Short swtxinc3;

    @Column(name = "SWTXINC4", nullable = false)
    private Short swtxinc4;

    @Column(name = "SWTXINC5", nullable = false)
    private Short swtxinc5;

    @Column(name = "EMAIL1", nullable = false, length = 50)
    private String email1;

    @Column(name = "EMAIL2", nullable = false, length = 50)
    private String email2;

    @Column(name = "WEBSITE", nullable = false, length = 100)
    private String website;

    @Column(name = "CTACPHONE", nullable = false, length = 30)
    private String ctacphone;

    @Column(name = "CTACFAX", nullable = false, length = 30)
    private String ctacfax;

    @Column(name = "DELMETHOD", nullable = false)
    private Short delmethod;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "RTGDAYS", nullable = false)
    private Short rtgdays;

    @Column(name = "RTGTERMS", nullable = false, length = 6)
    private String rtgterms;

    @Column(name = "RTGAMTTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamttc;

    @Column(name = "RTGAMTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamthc;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "NEXTCUID", nullable = false)
    private Integer nextcuid;

    @Column(name = "LEGALNAME", nullable = false, length = 60)
    private String legalname;

    @Column(name = "CHK1099AMT", nullable = false)
    private Short chk1099amt;

    @Column(name = "IDCUST", nullable = false, length = 12)
    private String idcust;

    @Column(name = "BRN", nullable = false, length = 30)
    private String brn;

}