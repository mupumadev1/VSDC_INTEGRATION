package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApibdId implements Serializable {
    private static final long serialVersionUID = 4659508498096002920L;
    @Column(name = "CNTBTCH", nullable = false, precision = 9)
    private BigDecimal cntbtch;

    @Column(name = "CNTITEM", nullable = false, precision = 7)
    private BigDecimal cntitem;

    @Column(name = "CNTLINE", nullable = false, precision = 5)
    private BigDecimal cntline;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApibdId entity = (ApibdId) o;
        return Objects.equals(this.cntbtch, entity.cntbtch) &&
                Objects.equals(this.cntitem, entity.cntitem) &&
                Objects.equals(this.cntline, entity.cntline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntbtch, cntitem, cntline);
    }

}