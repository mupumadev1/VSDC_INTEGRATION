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
public class PorcpalId implements java.io.Serializable {
    private static final long serialVersionUID = -8252019690154685859L;
    @Column(name = "DAYENDSEQ", nullable = false, precision = 19)
    private BigDecimal dayendseq;

    @Column(name = "RCPAHSEQ", nullable = false, precision = 19)
    private BigDecimal rcpahseq;

    @Column(name = "RCPALSEQ", nullable = false, precision = 19)
    private BigDecimal rcpalseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PorcpalId entity = (PorcpalId) o;
        return Objects.equals(this.rcpalseq, entity.rcpalseq) &&
                Objects.equals(this.dayendseq, entity.dayendseq) &&
                Objects.equals(this.rcpahseq, entity.rcpahseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcpalseq, dayendseq, rcpahseq);
    }

}