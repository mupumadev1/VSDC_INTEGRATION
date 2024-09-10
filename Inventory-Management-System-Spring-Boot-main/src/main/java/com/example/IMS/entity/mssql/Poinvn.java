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
@Table(name = "POINVN")
public class Poinvn {
    @EmbeddedId
    private PoinvnId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "ITEMDESC", nullable = false, length = 60)
    private String itemdesc;

    @Column(name = "STOCKUNIT", nullable = false, length = 10)
    private String stockunit;

    @Column(name = "RCPUNIT", nullable = false, length = 10)
    private String rcpunit;

    @Column(name = "RCPCONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal rcpconv;

    @Column(name = "RCPDECML", nullable = false)
    private Short rcpdecml;

    @Column(name = "RQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqreceived;

    @Column(name = "SQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqreceived;

    @Column(name = "OQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqreceived;

    @Column(name = "UNITWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitweight;

    @Column(name = "EXTWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal extweight;

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

    @Column(name = "TXBASEALLO", nullable = false, precision = 19, scale = 3)
    private BigDecimal txbaseallo;

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

    @Column(name = "DISCPCT", nullable = false, precision = 9, scale = 5)
    private BigDecimal discpct;

    @Column(name = "DISCOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal discount;

    @Column(name = "DISCOUNTF", nullable = false, precision = 19, scale = 3)
    private BigDecimal discountf;

    @Column(name = "FULLYINV", nullable = false)
    private Short fullyinv;

    @Column(name = "BILLRATE", nullable = false, precision = 19, scale = 6)
    private BigDecimal billrate;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "RTGDAYS", nullable = false)
    private Short rtgdays;

    @Column(name = "RTGAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamount;

    @Column(name = "RTGDATEDUE", nullable = false, precision = 9)
    private BigDecimal rtgdatedue;

    @Column(name = "RTGAMTOVER", nullable = false)
    private Short rtgamtover;

    @Column(name = "RTGDDTOVER", nullable = false)
    private Short rtgddtover;

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

    @Column(name = "RAXBASE1", nullable = false, precision = 19, scale = 3)
    private BigDecimal raxbase1;

    @Column(name = "RAXBASE2", nullable = false, precision = 19, scale = 3)
    private BigDecimal raxbase2;

    @Column(name = "RAXBASE3", nullable = false, precision = 19, scale = 3)
    private BigDecimal raxbase3;

    @Column(name = "RAXBASE4", nullable = false, precision = 19, scale = 3)
    private BigDecimal raxbase4;

    @Column(name = "RAXBASE5", nullable = false, precision = 19, scale = 3)
    private BigDecimal raxbase5;

    @Column(name = "RXRECVAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxrecvamt1;

    @Column(name = "RXRECVAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxrecvamt2;

    @Column(name = "RXRECVAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxrecvamt3;

    @Column(name = "RXRECVAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxrecvamt4;

    @Column(name = "RXRECVAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxrecvamt5;

    @Column(name = "RXEXPSAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxexpsamt1;

    @Column(name = "RXEXPSAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxexpsamt2;

    @Column(name = "RXEXPSAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxexpsamt3;

    @Column(name = "RXEXPSAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxexpsamt4;

    @Column(name = "RXEXPSAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxexpsamt5;

    @Column(name = "RXALLOAMT1", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxalloamt1;

    @Column(name = "RXALLOAMT2", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxalloamt2;

    @Column(name = "RXALLOAMT3", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxalloamt3;

    @Column(name = "RXALLOAMT4", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxalloamt4;

    @Column(name = "RXALLOAMT5", nullable = false, precision = 19, scale = 3)
    private BigDecimal rxalloamt5;

    @Column(name = "WEIGHTUNIT", nullable = false, length = 10)
    private String weightunit;

    @Column(name = "WEIGHTCONV", nullable = false, precision = 19, scale = 6)
    private BigDecimal weightconv;

    @Column(name = "DEFUWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal defuweight;

    @Column(name = "DEFEXTWGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal defextwght;

}