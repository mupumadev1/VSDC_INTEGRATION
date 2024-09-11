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
public class ApibId implements Serializable {
    private static final long serialVersionUID = -8907559163665063833L;
    @Column(name = "CNTBTCH", nullable = false, precision = 9)
    private BigDecimal cntbtch;

    @Column(name = "CNTITEM", nullable = false, precision = 7)
    private BigDecimal cntitem;

    @Column(name = "CNTPAYM", nullable = false, precision = 5)
    private BigDecimal cntpaym;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApibId entity = (ApibId) o;
        return Objects.equals(this.cntpaym, entity.cntpaym) &&
                Objects.equals(this.cntbtch, entity.cntbtch) &&
                Objects.equals(this.cntitem, entity.cntitem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntpaym, cntbtch, cntitem);
    }

}