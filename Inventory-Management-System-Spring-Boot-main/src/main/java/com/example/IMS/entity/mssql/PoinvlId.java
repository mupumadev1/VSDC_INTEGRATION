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
public class PoinvlId implements Serializable {
    private static final long serialVersionUID = -8136453263354811605L;
    @Column(name = "INVHSEQ", nullable = false, precision = 19)
    private BigDecimal invhseq;

    @Column(name = "INVLREV", nullable = false, precision = 19)
    private BigDecimal invlrev;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoinvlId entity = (PoinvlId) o;
        return Objects.equals(this.invhseq, entity.invhseq) &&
                Objects.equals(this.invlrev, entity.invlrev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invhseq, invlrev);
    }

}