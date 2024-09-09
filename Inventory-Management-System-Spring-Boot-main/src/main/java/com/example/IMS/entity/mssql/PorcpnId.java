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
public class PorcpnId implements java.io.Serializable {
    private static final long serialVersionUID = 6541994457588715283L;
    @Column(name = "RCPHSEQ", nullable = false, precision = 19)
    private BigDecimal rcphseq;

    @Column(name = "RCPLSEQ", nullable = false, precision = 19)
    private BigDecimal rcplseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PorcpnId entity = (PorcpnId) o;
        return Objects.equals(this.rcplseq, entity.rcplseq) &&
                Objects.equals(this.rcphseq, entity.rcphseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcplseq, rcphseq);
    }

}