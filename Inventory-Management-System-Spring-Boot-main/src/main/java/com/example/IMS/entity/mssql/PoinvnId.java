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
public class PoinvnId implements Serializable {
    private static final long serialVersionUID = 7513496085929474652L;
    @Column(name = "INVHSEQ", nullable = false, precision = 19)
    private BigDecimal invhseq;

    @Column(name = "INVLSEQ", nullable = false, precision = 19)
    private BigDecimal invlseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoinvnId entity = (PoinvnId) o;
        return Objects.equals(this.invlseq, entity.invlseq) &&
                Objects.equals(this.invhseq, entity.invhseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invlseq, invhseq);
    }

}