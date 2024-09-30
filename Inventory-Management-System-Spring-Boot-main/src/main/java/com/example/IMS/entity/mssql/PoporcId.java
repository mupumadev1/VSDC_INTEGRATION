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
public class PoporcId implements Serializable {
    private static final long serialVersionUID = 8017245339308657780L;
    @Column(name = "PORHSEQ", nullable = false, precision = 19)
    private BigDecimal porhseq;

    @Column(name = "PORCREV", nullable = false, precision = 19)
    private BigDecimal porcrev;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoporcId entity = (PoporcId) o;
        return Objects.equals(this.porhseq, entity.porhseq) &&
                Objects.equals(this.porcrev, entity.porcrev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(porhseq, porcrev);
    }

}