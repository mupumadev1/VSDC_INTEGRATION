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
@Table(name = "APIBD")
public class Apibd {
    @EmbeddedId
    private ApibdId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "IDDIST", nullable = false, length = 6)
    private String iddist;

    @Column(name = "TEXTDESC", nullable = false, length = 60)
    private String textdesc;

    @Column(name = "SWMANLDIST", nullable = false)
    private Short swmanldist;

    @Column(name = "AMTTOTTAX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttottax;

    @Column(name = "SWMANLTX", nullable = false)
    private Short swmanltx;

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

    @Column(name = "RATETAX1", nullable = false, precision = 15, scale = 5)
    private BigDecimal ratetax1;

    @Column(name = "RATETAX2", nullable = false, precision = 15, scale = 5)
    private BigDecimal ratetax2;

    @Column(name = "RATETAX3", nullable = false, precision = 15, scale = 5)
    private BigDecimal ratetax3;

    @Column(name = "RATETAX4", nullable = false, precision = 15, scale = 5)
    private BigDecimal ratetax4;

    @Column(name = "RATETAX5", nullable = false, precision = 15, scale = 5)
    private BigDecimal ratetax5;

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

    @Column(name = "IDGLACCT", nullable = false, length = 45)
    private String idglacct;

    @Column(name = "IDACCTTAX", nullable = false, length = 45)
    private String idaccttax;

    @Column(name = "ID1099CLAS", nullable = false, length = 6)
    private String id1099clas;

    @Column(name = "AMTDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdist;

    @Column(name = "AMTDISTNET", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdistnet;

    @Column(name = "AMTINCLTAX", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtincltax;

    @Column(name = "AMTGLDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtgldist;

    @Column(name = "AMTTAXREC1", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxrec1;

    @Column(name = "AMTTAXREC2", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxrec2;

    @Column(name = "AMTTAXREC3", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxrec3;

    @Column(name = "AMTTAXREC4", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxrec4;

    @Column(name = "AMTTAXREC5", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxrec5;

    @Column(name = "AMTTAXEXP1", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxexp1;

    @Column(name = "AMTTAXEXP2", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxexp2;

    @Column(name = "AMTTAXEXP3", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxexp3;

    @Column(name = "AMTTAXEXP4", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxexp4;

    @Column(name = "AMTTAXEXP5", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxexp5;

    @Column(name = "AMTTAXTOBE", nullable = false, precision = 19, scale = 3)
    private BigDecimal amttaxtobe;

    @Column(name = "CONTRACT", nullable = false, length = 16)
    private String contract;

    @Column(name = "PROJECT", nullable = false, length = 16)
    private String project;

    @Column(name = "CATEGORY", nullable = false, length = 16)
    private String category;

    @Column(name = "RESOURCE", nullable = false, length = 24)
    private String resource;

    @Column(name = "TRANSNBR", nullable = false)
    private Integer transnbr;

    @Column(name = "COSTCLASS", nullable = false)
    private Short costclass;

    @Column(name = "BILLTYPE", nullable = false)
    private Short billtype;

    @Column(name = "IDITEM", nullable = false, length = 16)
    private String iditem;

    @Column(name = "UNITMEAS", nullable = false, length = 10)
    private String unitmeas;

    @Column(name = "QTYINVC", nullable = false, precision = 19, scale = 5)
    private BigDecimal qtyinvc;

    @Column(name = "AMTCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal amtcost;

    @Column(name = "BILLDATE", nullable = false, precision = 9)
    private BigDecimal billdate;

    @Column(name = "BILLRATE", nullable = false, precision = 19, scale = 6)
    private BigDecimal billrate;

    @Column(name = "BILLCURN", nullable = false, length = 3)
    private String billcurn;

    @Column(name = "SWIBT", nullable = false)
    private Short swibt;

    @Column(name = "SWDISCABL", nullable = false)
    private Short swdiscabl;

    @Column(name = "OCNTLINE", nullable = false, precision = 5)
    private BigDecimal ocntline;

    @Column(name = "RTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamt;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "RTGDAYS", nullable = false)
    private Short rtgdays;

    @Column(name = "RTGDATEDUE", nullable = false, precision = 9)
    private BigDecimal rtgdatedue;

    @Column(name = "SWRTGDDTOV", nullable = false)
    private Short swrtgddtov;

    @Column(name = "SWRTGAMTOV", nullable = false)
    private Short swrtgamtov;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "DESCOMP", nullable = false, length = 6)
    private String descomp;

    @Column(name = "ROUTE", nullable = false)
    private Short route;

    @Column(name = "RTGDISTTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgdisttc;

    @Column(name = "RTGINVDIST", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtginvdist;

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

    @Column(name = "TXEXP1RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp1rc;

    @Column(name = "TXEXP2RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp2rc;

    @Column(name = "TXEXP3RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp3rc;

    @Column(name = "TXEXP4RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp4rc;

    @Column(name = "TXEXP5RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp5rc;

    @Column(name = "TXREC1RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec1rc;

    @Column(name = "TXREC2RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec2rc;

    @Column(name = "TXREC3RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec3rc;

    @Column(name = "TXREC4RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec4rc;

    @Column(name = "TXREC5RC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec5rc;

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

    @Column(name = "TXAMTRT1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt1hc;

    @Column(name = "TXAMTRT2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt2hc;

    @Column(name = "TXAMTRT3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt3hc;

    @Column(name = "TXAMTRT4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt4hc;

    @Column(name = "TXAMTRT5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txamtrt5hc;

    @Column(name = "TXREC1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec1hc;

    @Column(name = "TXREC2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec2hc;

    @Column(name = "TXREC3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec3hc;

    @Column(name = "TXREC4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec4hc;

    @Column(name = "TXREC5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txrec5hc;

    @Column(name = "TXEXP1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp1hc;

    @Column(name = "TXEXP2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp2hc;

    @Column(name = "TXEXP3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp3hc;

    @Column(name = "TXEXP4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp4hc;

    @Column(name = "TXEXP5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexp5hc;

    @Column(name = "TXALLHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txallhc;

    @Column(name = "TXALL1HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall1hc;

    @Column(name = "TXALL2HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall2hc;

    @Column(name = "TXALL3HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall3hc;

    @Column(name = "TXALL4HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall4hc;

    @Column(name = "TXALL5HC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall5hc;

    @Column(name = "TXALL1TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall1tc;

    @Column(name = "TXALL2TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall2tc;

    @Column(name = "TXALL3TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall3tc;

    @Column(name = "TXALL4TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall4tc;

    @Column(name = "TXALL5TC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txall5tc;

    @Column(name = "AMTCOSTHC", nullable = false, precision = 19, scale = 6)
    private BigDecimal amtcosthc;

    @Column(name = "AMTDISTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdisthc;

    @Column(name = "DISTNETHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal distnethc;

    @Column(name = "RTGAMTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamthc;

    @Column(name = "TXALLRTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txallrthc;

    @Column(name = "TXALLRTTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txallrttc;

    @Column(name = "TXEXPRTHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexprthc;

    @Column(name = "TXEXPRTTC", nullable = false, precision = 19, scale = 3)
    private BigDecimal txexprttc;

    @Column(name = "SWFAS", nullable = false)
    private Short swfas;

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

    @Column(name = "SWCAXABLE1", nullable = false)
    private Short swcaxable1;

    @Column(name = "SWCAXABLE2", nullable = false)
    private Short swcaxable2;

    @Column(name = "SWCAXABLE3", nullable = false)
    private Short swcaxable3;

    @Column(name = "SWCAXABLE4", nullable = false)
    private Short swcaxable4;

    @Column(name = "SWCAXABLE5", nullable = false)
    private Short swcaxable5;

}