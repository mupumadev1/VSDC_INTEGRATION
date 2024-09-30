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
public class PoprxlId implements Serializable {
    private static final long serialVersionUID = -3372213210139892612L;
    @Column(name = "PRORSEQ", nullable = false, precision = 19)
    private BigDecimal prorseq;

    @Column(name = "LINESEQ", nullable = false, precision = 19)
    private BigDecimal lineseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoprxlId entity = (PoprxlId) o;
        return Objects.equals(this.lineseq, entity.lineseq) &&
                Objects.equals(this.prorseq, entity.prorseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineseq, prorseq);
    }

}