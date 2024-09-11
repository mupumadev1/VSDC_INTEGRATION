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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ApibhId implements Serializable {
    private static final long serialVersionUID = -2403418385644516235L;
    @Column(name = "CNTBTCH", nullable = false, precision = 9)
    private BigDecimal cntbtch;

    @Column(name = "CNTITEM", nullable = false, precision = 7)
    private BigDecimal cntitem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApibhId entity = (ApibhId) o;
        return Objects.equals(this.cntbtch, entity.cntbtch) &&
                Objects.equals(this.cntitem, entity.cntitem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntbtch, cntitem);
    }

}