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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "POPORL", schema = "dbo")
public class Poporl {
    @EmbeddedId
    private PoporlId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "PORLSEQ", nullable = false, precision = 19)
    private BigDecimal porlseq;

    @Column(name = "PORCSEQ", nullable = false, precision = 19)
    private BigDecimal porcseq;

    @Column(name = "INDBTABLE", nullable = false)
    private Short indbtable;

    @Column(name = "CONSOLSEQ", nullable = false, precision = 19)
    private BigDecimal consolseq;

    @Column(name = "RQNHSEQ", nullable = false, precision = 19)
    private BigDecimal rqnhseq;

    @Column(name = "RQNLSEQ", nullable = false, precision = 19)
    private BigDecimal rqnlseq;

    @Column(name = "OEONUMBER", nullable = false, length = 22)
    private String oeonumber;

    @Column(name = "POSTEDTOIC", nullable = false)
    private Short postedtoic;

    @Column(name = "TOPOSTTOIC", nullable = false)
    private Short toposttoic;

    @Column(name = "STPRINT", nullable = false)
    private Short stprint;

    @Column(name = "COMPLETION", nullable = false)
    private Short completion;

    @Column(name = "DTCOMPLETE", nullable = false, precision = 9)
    private BigDecimal dtcomplete;

    @Column(name = "ITEMEXISTS", nullable = false)
    private Short itemexists;

    @Column(name = "ITEMNO", nullable = false, length = 24)
    private String itemno;

    @Column(name = "LOCATION", nullable = false, length = 6)
    private String location;

    @Column(name = "ITEMDESC", nullable = false, length = 60)
    private String itemdesc;

    @Column(name = "EXPARRIVAL", nullable = false, precision = 9)
    private BigDecimal exparrival;

    @Column(name = "VENDITEMNO", nullable = false, length = 24)
    private String venditemno;

    @Column(name = "HASCOMMENT", nullable = false)
    private Short hascomment;

    @Column(name = "ORDERUNIT", nullable = false, length = 10)
    private String orderunit;

    @Column(name = "ORDERCONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal orderconv;

    @Column(name = "ORDERDECML", nullable = false)
    private Short orderdecml;

    @Column(name = "STOCKDECML", nullable = false)
    private Short stockdecml;

    @Column(name = "OQORDERED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqordered;

    @Column(name = "OQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqreceived;

    @Column(name = "OQCANCELED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqcanceled;

    @Column(name = "OQRCPEXTRA", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqrcpextra;

    @Column(name = "OQOUTSTAND", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqoutstand;

    @Column(name = "SQORDERED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqordered;

    @Column(name = "SQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqreceived;

    @Column(name = "SQCANCELED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqcanceled;

    @Column(name = "SQRCPEXTRA", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqrcpextra;

    @Column(name = "SQSETTLED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqsettled;

    @Column(name = "SQOUTSTAND", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqoutstand;

    @Column(name = "UNITWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitweight;

    @Column(name = "EXTWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal extweight;

    @Column(name = "OQRCPDAYS", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqrcpdays;

    @Column(name = "EXTRECEIVE", nullable = false, precision = 19, scale = 3)
    private BigDecimal extreceive;

    @Column(name = "EXTCANCEL", nullable = false, precision = 19, scale = 3)
    private BigDecimal extcancel;

    @Column(name = "SRRECEIVED", nullable = false, precision = 19, scale = 3)
    private BigDecimal srreceived;

    @Column(name = "UNITCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal unitcost;

    @Column(name = "EXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal extended;

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

    @Column(name = "TAXRATE1", nullable = false, precision = 15, scale = 5)
    private BigDecimal taxrate1;

    @Column(name = "TAXRATE2", nullable = false, precision = 15, scale = 5)
    private BigDecimal taxrate2;

    @Column(name = "TAXRATE3", nullable = false, precision = 15, scale = 5)
    private BigDecimal taxrate3;

    @Column(name = "TAXRATE4", nullable = false, precision = 15, scale = 5)
    private BigDecimal taxrate4;

    @Column(name = "TAXRATE5", nullable = false, precision = 15, scale = 5)
    private BigDecimal taxrate5;

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

    @Column(name = "FCEXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcextended;

    @Column(name = "GLACEXPENS", nullable = false, length = 45)
    private String glacexpens;

    @Column(name = "HASDROPSHI", nullable = false)
    private Short hasdropshi;

    @Column(name = "DROPTYPE", nullable = false)
    private Short droptype;

    @Column(name = "IDCUST", nullable = false, length = 12)
    private String idcust;

    @Column(name = "IDCUSTSHPT", nullable = false, length = 6)
    private String idcustshpt;

    @Column(name = "DLOCATION", nullable = false, length = 6)
    private String dlocation;

    @Column(name = "\"DESC\"", nullable = false, length = 60)
    private String desc;

    @Column(name = "ADDRESS1", nullable = false, length = 60)
    private String address1;

    @Column(name = "ADDRESS2", nullable = false, length = 60)
    private String address2;

    @Column(name = "ADDRESS3", nullable = false, length = 60)
    private String address3;

    @Column(name = "ADDRESS4", nullable = false, length = 60)
    private String address4;

    @Column(name = "CITY", nullable = false, length = 30)
    private String city;

    @Column(name = "STATE", nullable = false, length = 30)
    private String state;

    @Column(name = "ZIP", nullable = false, length = 20)
    private String zip;

    @Column(name = "COUNTRY", nullable = false, length = 30)
    private String country;

    @Column(name = "PHONE", nullable = false, length = 30)
    private String phone;

    @Column(name = "FAX", nullable = false, length = 30)
    private String fax;

    @Column(name = "CONTACT", nullable = false, length = 60)
    private String contact;

    @Column(name = "STOCKITEM", nullable = false)
    private Short stockitem;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "PHONEC", nullable = false, length = 30)
    private String phonec;

    @Column(name = "FAXC", nullable = false, length = 30)
    private String faxc;

    @Column(name = "EMAILC", nullable = false, length = 50)
    private String emailc;

    @Column(name = "GLNONSTKCR", nullable = false, length = 45)
    private String glnonstkcr;

    @Column(name = "MANITEMNO", nullable = false, length = 24)
    private String manitemno;

    @Column(name = "DISCPCT", nullable = false, precision = 9, scale = 5)
    private BigDecimal discpct;

    @Column(name = "DISCOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal discount;

    @Column(name = "\"VALUES\"", nullable = false)
    private Integer values;

    @Column(name = "DISCOUNTF", nullable = false, precision = 19, scale = 3)
    private BigDecimal discountf;

    @Column(name = "ISRECEIVED", nullable = false)
    private Short isreceived;

    @Column(name = "AGENTLSEQ", nullable = false, precision = 19)
    private BigDecimal agentlseq;

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

    @Column(name = "TFBASEALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfbaseallo;

    @Column(name = "TFINCLUDE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfinclude1;

    @Column(name = "TFINCLUDE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfinclude2;

    @Column(name = "TFINCLUDE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfinclude3;

    @Column(name = "TFINCLUDE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfinclude4;

    @Column(name = "TFINCLUDE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfinclude5;

    @Column(name = "TFALLOAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfalloamt1;

    @Column(name = "TFALLOAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfalloamt2;

    @Column(name = "TFALLOAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfalloamt3;

    @Column(name = "TFALLOAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfalloamt4;

    @Column(name = "TFALLOAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfalloamt5;

    @Column(name = "TFRECVAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfrecvamt1;

    @Column(name = "TFRECVAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfrecvamt2;

    @Column(name = "TFRECVAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfrecvamt3;

    @Column(name = "TFRECVAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfrecvamt4;

    @Column(name = "TFRECVAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfrecvamt5;

    @Column(name = "TFEXPSAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfexpsamt1;

    @Column(name = "TFEXPSAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfexpsamt2;

    @Column(name = "TFEXPSAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfexpsamt3;

    @Column(name = "TFEXPSAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfexpsamt4;

    @Column(name = "TFEXPSAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal tfexpsamt5;

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

    @Column(name = "UCISMANUAL", nullable = false)
    private Short ucismanual;

    @Column(name = "WEIGHTUNIT", nullable = false, length = 10)
    private String weightunit;

    @Column(name = "WEIGHTCONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal weightconv;

    @Column(name = "DEFUWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal defuweight;

    @Column(name = "DEFEXTWGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal defextwght;

    @Column(name = "COPYDETAIL", nullable = false)
    private Short copydetail;

    @Column(name = "DETAILNUM", nullable = false)
    private Short detailnum;

    @Column(name = "CAXABLE1", nullable = false)
    private Short caxable1;

    @Column(name = "CAXABLE2", nullable = false)
    private Short caxable2;

    @Column(name = "CAXABLE3", nullable = false)
    private Short caxable3;

    @Column(name = "CAXABLE4", nullable = false)
    private Short caxable4;

    @Column(name = "CAXABLE5", nullable = false)
    private Short caxable5;

}