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
@Table(name = "PORCPN", schema = "dbo")
public class Porcpn {
    @EmbeddedId
    private PorcpnId id;

    public Porcpn(PorcpnId id, BigDecimal audtdate, BigDecimal audttime, String audtuser, String audtorg, String itemdesc, String stockunit, String rcpunit, BigDecimal rcpconv, Short rcpdecml, BigDecimal rqreceived, BigDecimal rqreturned, BigDecimal rqcanceled, BigDecimal sqreceived, BigDecimal sqcanceled, BigDecimal oqreceived, BigDecimal oqcanceled, BigDecimal rqpofilled, BigDecimal sqpofilled, BigDecimal oqpofilled, BigDecimal unitweight, BigDecimal extweight, BigDecimal unitcost, BigDecimal extended, BigDecimal mprorated, Short rcpdays, BigDecimal taxbase1, BigDecimal taxbase2, BigDecimal taxbase3, BigDecimal taxbase4, BigDecimal taxbase5, Short taxinclud1, Short taxinclud2, Short taxinclud3, Short taxinclud4, Short taxinclud5, BigDecimal txbaseallo, BigDecimal txinclude1, BigDecimal txinclude2, BigDecimal txinclude3, BigDecimal txinclude4, BigDecimal txinclude5, BigDecimal txrecvamt1, BigDecimal txrecvamt2, BigDecimal txrecvamt3, BigDecimal txrecvamt4, BigDecimal txrecvamt5, BigDecimal txexpsamt1, BigDecimal txexpsamt2, BigDecimal txexpsamt3, BigDecimal txexpsamt4, BigDecimal txexpsamt5, BigDecimal txalloamt1, BigDecimal txalloamt2, BigDecimal txalloamt3, BigDecimal txalloamt4, BigDecimal txalloamt5, BigDecimal tfbaseallo, BigDecimal tfinclude1, BigDecimal tfinclude2, BigDecimal tfinclude3, BigDecimal tfinclude4, BigDecimal tfinclude5, BigDecimal tfalloamt1, BigDecimal tfalloamt2, BigDecimal tfalloamt3, BigDecimal tfalloamt4, BigDecimal tfalloamt5, BigDecimal tfrecvamt1, BigDecimal tfrecvamt2, BigDecimal tfrecvamt3, BigDecimal tfrecvamt4, BigDecimal tfrecvamt5, BigDecimal tfexpsamt1, BigDecimal tfexpsamt2, BigDecimal tfexpsamt3, BigDecimal tfexpsamt4, BigDecimal tfexpsamt5, BigDecimal discpct, BigDecimal discount, BigDecimal discountf, BigDecimal billrate, BigDecimal rtgpercent, Short rtgdays, BigDecimal rtgamount, Short rtgamtover, BigDecimal trinclude1, BigDecimal trinclude2, BigDecimal trinclude3, BigDecimal trinclude4, BigDecimal trinclude5, BigDecimal trrecvamt1, BigDecimal trrecvamt2, BigDecimal trrecvamt3, BigDecimal trrecvamt4, BigDecimal trrecvamt5, BigDecimal trexpsamt1, BigDecimal trexpsamt2, BigDecimal trexpsamt3, BigDecimal trexpsamt4, BigDecimal trexpsamt5, BigDecimal tralloamt1, BigDecimal tralloamt2, BigDecimal tralloamt3, BigDecimal tralloamt4, BigDecimal tralloamt5, BigDecimal raxbase1, BigDecimal raxbase2, BigDecimal raxbase3, BigDecimal raxbase4, BigDecimal raxbase5, BigDecimal rxrecvamt1, BigDecimal rxrecvamt2, BigDecimal rxrecvamt3, BigDecimal rxrecvamt4, BigDecimal rxrecvamt5, BigDecimal rxexpsamt1, BigDecimal rxexpsamt2, BigDecimal rxexpsamt3, BigDecimal rxexpsamt4, BigDecimal rxexpsamt5, BigDecimal rxalloamt1, BigDecimal rxalloamt2, BigDecimal rxalloamt3, BigDecimal rxalloamt4, BigDecimal rxalloamt5, String weightunit, BigDecimal weightconv, BigDecimal defuweight, BigDecimal defextwght) {
        this.id = id;
        this.audtdate = audtdate;
        this.audttime = audttime;
        this.audtuser = audtuser;
        this.audtorg = audtorg;
        this.itemdesc = itemdesc;
        this.stockunit = stockunit;
        this.rcpunit = rcpunit;
        this.rcpconv = rcpconv;
        this.rcpdecml = rcpdecml;
        this.rqreceived = rqreceived;
        this.rqreturned = rqreturned;
        this.rqcanceled = rqcanceled;
        this.sqreceived = sqreceived;
        this.sqcanceled = sqcanceled;
        this.oqreceived = oqreceived;
        this.oqcanceled = oqcanceled;
        this.rqpofilled = rqpofilled;
        this.sqpofilled = sqpofilled;
        this.oqpofilled = oqpofilled;
        this.unitweight = unitweight;
        this.extweight = extweight;
        this.unitcost = unitcost;
        this.extended = extended;
        this.mprorated = mprorated;
        this.rcpdays = rcpdays;
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
        this.txbaseallo = txbaseallo;
        this.txinclude1 = txinclude1;
        this.txinclude2 = txinclude2;
        this.txinclude3 = txinclude3;
        this.txinclude4 = txinclude4;
        this.txinclude5 = txinclude5;
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
        this.txalloamt1 = txalloamt1;
        this.txalloamt2 = txalloamt2;
        this.txalloamt3 = txalloamt3;
        this.txalloamt4 = txalloamt4;
        this.txalloamt5 = txalloamt5;
        this.tfbaseallo = tfbaseallo;
        this.tfinclude1 = tfinclude1;
        this.tfinclude2 = tfinclude2;
        this.tfinclude3 = tfinclude3;
        this.tfinclude4 = tfinclude4;
        this.tfinclude5 = tfinclude5;
        this.tfalloamt1 = tfalloamt1;
        this.tfalloamt2 = tfalloamt2;
        this.tfalloamt3 = tfalloamt3;
        this.tfalloamt4 = tfalloamt4;
        this.tfalloamt5 = tfalloamt5;
        this.tfrecvamt1 = tfrecvamt1;
        this.tfrecvamt2 = tfrecvamt2;
        this.tfrecvamt3 = tfrecvamt3;
        this.tfrecvamt4 = tfrecvamt4;
        this.tfrecvamt5 = tfrecvamt5;
        this.tfexpsamt1 = tfexpsamt1;
        this.tfexpsamt2 = tfexpsamt2;
        this.tfexpsamt3 = tfexpsamt3;
        this.tfexpsamt4 = tfexpsamt4;
        this.tfexpsamt5 = tfexpsamt5;
        this.discpct = discpct;
        this.discount = discount;
        this.discountf = discountf;
        this.billrate = billrate;
        this.rtgpercent = rtgpercent;
        this.rtgdays = rtgdays;
        this.rtgamount = rtgamount;
        this.rtgamtover = rtgamtover;
        this.trinclude1 = trinclude1;
        this.trinclude2 = trinclude2;
        this.trinclude3 = trinclude3;
        this.trinclude4 = trinclude4;
        this.trinclude5 = trinclude5;
        this.trrecvamt1 = trrecvamt1;
        this.trrecvamt2 = trrecvamt2;
        this.trrecvamt3 = trrecvamt3;
        this.trrecvamt4 = trrecvamt4;
        this.trrecvamt5 = trrecvamt5;
        this.trexpsamt1 = trexpsamt1;
        this.trexpsamt2 = trexpsamt2;
        this.trexpsamt3 = trexpsamt3;
        this.trexpsamt4 = trexpsamt4;
        this.trexpsamt5 = trexpsamt5;
        this.tralloamt1 = tralloamt1;
        this.tralloamt2 = tralloamt2;
        this.tralloamt3 = tralloamt3;
        this.tralloamt4 = tralloamt4;
        this.tralloamt5 = tralloamt5;
        this.raxbase1 = raxbase1;
        this.raxbase2 = raxbase2;
        this.raxbase3 = raxbase3;
        this.raxbase4 = raxbase4;
        this.raxbase5 = raxbase5;
        this.rxrecvamt1 = rxrecvamt1;
        this.rxrecvamt2 = rxrecvamt2;
        this.rxrecvamt3 = rxrecvamt3;
        this.rxrecvamt4 = rxrecvamt4;
        this.rxrecvamt5 = rxrecvamt5;
        this.rxexpsamt1 = rxexpsamt1;
        this.rxexpsamt2 = rxexpsamt2;
        this.rxexpsamt3 = rxexpsamt3;
        this.rxexpsamt4 = rxexpsamt4;
        this.rxexpsamt5 = rxexpsamt5;
        this.rxalloamt1 = rxalloamt1;
        this.rxalloamt2 = rxalloamt2;
        this.rxalloamt3 = rxalloamt3;
        this.rxalloamt4 = rxalloamt4;
        this.rxalloamt5 = rxalloamt5;
        this.weightunit = weightunit;
        this.weightconv = weightconv;
        this.defuweight = defuweight;
        this.defextwght = defextwght;
    }

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

    @Column(name = "RQRETURNED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqreturned;

    @Column(name = "RQCANCELED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqcanceled;

    @Column(name = "SQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqreceived;

    @Column(name = "SQCANCELED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqcanceled;

    @Column(name = "OQRECEIVED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqreceived;

    @Column(name = "OQCANCELED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqcanceled;

    @Column(name = "RQPOFILLED", nullable = false, precision = 19, scale = 4)
    private BigDecimal rqpofilled;

    @Column(name = "SQPOFILLED", nullable = false, precision = 19, scale = 4)
    private BigDecimal sqpofilled;

    @Column(name = "OQPOFILLED", nullable = false, precision = 19, scale = 4)
    private BigDecimal oqpofilled;

    @Column(name = "UNITWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitweight;

    @Column(name = "EXTWEIGHT", nullable = false, precision = 19, scale = 4)
    private BigDecimal extweight;

    @Column(name = "UNITCOST", nullable = false, precision = 19, scale = 6)
    private BigDecimal unitcost;

    @Column(name = "EXTENDED", nullable = false, precision = 19, scale = 3)
    private BigDecimal extended;

    @Column(name = "MPRORATED", nullable = false, precision = 19, scale = 3)
    private BigDecimal mprorated;

    @Column(name = "RCPDAYS", nullable = false)
    private Short rcpdays;

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

    @Column(name = "BILLRATE", nullable = false, precision = 19, scale = 6)
    private BigDecimal billrate;

    @Column(name = "RTGPERCENT", nullable = false, precision = 9, scale = 5)
    private BigDecimal rtgpercent;

    @Column(name = "RTGDAYS", nullable = false)
    private Short rtgdays;

    @Column(name = "RTGAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal rtgamount;

    @Column(name = "RTGAMTOVER", nullable = false)
    private Short rtgamtover;

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