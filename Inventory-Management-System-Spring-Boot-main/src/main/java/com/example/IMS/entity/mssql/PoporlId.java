package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoporlId implements java.io.Serializable {
    private static final long serialVersionUID = 1340043762196484147L;
    @Column(name = "PORHSEQ", nullable = false, precision = 19)
    private BigDecimal porhseq;

    @Column(name = "PORLREV", nullable = false, precision = 19)
    private BigDecimal porlrev;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoporlId entity = (PoporlId) o;
        return Objects.equals(this.porhseq, entity.porhseq) &&
                Objects.equals(this.porlrev, entity.porlrev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(porhseq, porlrev);
    }

}