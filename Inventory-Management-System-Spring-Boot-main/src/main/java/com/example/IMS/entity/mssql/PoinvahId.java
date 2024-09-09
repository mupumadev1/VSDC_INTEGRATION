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
public class PoinvahId implements Serializable {
    private static final long serialVersionUID = 8354438363396769737L;
    @Column(name = "DAYENDSEQ", nullable = false, precision = 19)
    private BigDecimal dayendseq;

    @Column(name = "INVAHSEQ", nullable = false, precision = 19)
    private BigDecimal invahseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoinvahId entity = (PoinvahId) o;
        return Objects.equals(this.dayendseq, entity.dayendseq) &&
                Objects.equals(this.invahseq, entity.invahseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayendseq, invahseq);
    }

}