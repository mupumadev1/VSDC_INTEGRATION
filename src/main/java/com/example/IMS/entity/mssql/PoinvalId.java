package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PoinvalId implements Serializable {
    private static final long serialVersionUID = -5541756825328118042L;
    @Column(name = "DAYENDSEQ", nullable = false, precision = 19)
    private BigDecimal dayendseq;

    @Column(name = "INVAHSEQ", nullable = false, precision = 19)
    private BigDecimal invahseq;

    @Column(name = "INVALSEQ", nullable = false, precision = 19)
    private BigDecimal invalseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoinvalId entity = (PoinvalId) o;
        return Objects.equals(this.dayendseq, entity.dayendseq) &&
                Objects.equals(this.invahseq, entity.invahseq) &&
                Objects.equals(this.invalseq, entity.invalseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayendseq, invahseq, invalseq);
    }

}