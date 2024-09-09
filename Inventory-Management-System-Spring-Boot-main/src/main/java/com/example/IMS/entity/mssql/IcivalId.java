package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class IcivalId implements java.io.Serializable {
    private static final long serialVersionUID = -4705648118446003200L;
    @Column(name = "ACCTSET", nullable = false, length = 6)
    private String acctset;

    @Column(name = "LOCATION", nullable = false, length = 6)
    private String location;

    @Column(name = "ITEMNO", nullable = false, length = 24)
    private String itemno;

    @Column(name = "FISCYEAR", nullable = false, length = 4)
    private String fiscyear;

    @Column(name = "FISCPERIOD", nullable = false)
    private Short fiscperiod;

    @Column(name = "TRANSDATE", nullable = false, precision = 9)
    private BigDecimal transdate;

    @Column(name = "DAYENDSEQ", nullable = false)
    private Integer dayendseq;

    @Column(name = "ENTRYSEQ", nullable = false)
    private Integer entryseq;

    @Column(name = "\"LINENO\"", nullable = false)
    private Short lineno;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IcivalId entity = (IcivalId) o;
        return Objects.equals(this.lineno, entity.lineno) &&
                Objects.equals(this.dayendseq, entity.dayendseq) &&
                Objects.equals(this.entryseq, entity.entryseq) &&
                Objects.equals(this.acctset, entity.acctset) &&
                Objects.equals(this.location, entity.location) &&
                Objects.equals(this.itemno, entity.itemno) &&
                Objects.equals(this.transdate, entity.transdate) &&
                Objects.equals(this.fiscperiod, entity.fiscperiod) &&
                Objects.equals(this.fiscyear, entity.fiscyear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineno, dayendseq, entryseq, acctset, location, itemno, transdate, fiscperiod, fiscyear);
    }

}