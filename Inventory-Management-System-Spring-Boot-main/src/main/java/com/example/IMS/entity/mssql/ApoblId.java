package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ApoblId implements Serializable {
    private static final long serialVersionUID = 3102335198457525016L;
    @Column(name = "IDVEND", nullable = false, length = 12)
    private String idvend;

    @Column(name = "IDINVC", nullable = false, length = 22)
    private String idinvc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApoblId entity = (ApoblId) o;
        return Objects.equals(this.idinvc, entity.idinvc) &&
                Objects.equals(this.idvend, entity.idvend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idinvc, idvend);
    }

}