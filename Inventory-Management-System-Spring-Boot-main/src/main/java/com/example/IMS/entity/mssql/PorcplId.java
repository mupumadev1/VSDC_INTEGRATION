package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PorcplId implements java.io.Serializable {
    private static final long serialVersionUID = 8483395981792253350L;
    @Column(name = "RCPHSEQ", nullable = false, precision = 19)
    private BigDecimal rcphseq;

    @Column(name = "RCPLREV", nullable = false, precision = 19)
    private BigDecimal rcplrev;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PorcplId entity = (PorcplId) o;
        return Objects.equals(this.rcplrev, entity.rcplrev) &&
                Objects.equals(this.rcphseq, entity.rcphseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcplrev, rcphseq);
    }

}