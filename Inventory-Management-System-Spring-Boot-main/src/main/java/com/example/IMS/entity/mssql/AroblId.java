package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AroblId implements java.io.Serializable {
    private static final long serialVersionUID = 4535987360652525099L;
    @Column(name = "IDCUST", nullable = false, length = 12)
    private String idcust;

    @Column(name = "IDINVC", nullable = false, length = 22)
    private String idinvc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AroblId entity = (AroblId) o;
        return Objects.equals(this.idcust, entity.idcust) &&
                Objects.equals(this.idinvc, entity.idinvc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcust, idinvc);
    }

}