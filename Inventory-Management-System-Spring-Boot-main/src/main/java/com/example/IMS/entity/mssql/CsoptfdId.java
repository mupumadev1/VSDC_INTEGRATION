package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CsoptfdId implements Serializable {
    private static final long serialVersionUID = -7759182425235369078L;
    @Column(name = "OPTFIELD", nullable = false, length = 12)
    private String optfield;

    @Column(name = "\"VALUE\"", nullable = false, length = 60)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CsoptfdId entity = (CsoptfdId) o;
        return Objects.equals(this.optfield, entity.optfield) &&
                Objects.equals(this.value, entity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optfield, value);
    }

}