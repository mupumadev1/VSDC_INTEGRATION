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
public class PorcpahId implements java.io.Serializable {
    private static final long serialVersionUID = 1470468232857754309L;
    @Column(name = "DAYENDSEQ", nullable = false, precision = 19)
    private BigDecimal dayendseq;

    @Column(name = "RCPAHSEQ", nullable = false, precision = 19)
    private BigDecimal rcpahseq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PorcpahId entity = (PorcpahId) o;
        return Objects.equals(this.dayendseq, entity.dayendseq) &&
                Objects.equals(this.rcpahseq, entity.rcpahseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayendseq, rcpahseq);
    }

}