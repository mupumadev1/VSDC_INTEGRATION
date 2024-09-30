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
public class IcadedId implements Serializable {
    private static final long serialVersionUID = 8141929720981830532L;
    @Column(name = "ADJENSEQ", nullable = false)
    private Integer adjenseq;

    @Column(name = "\"LINENO\"", nullable = false)
    private Short lineno;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IcadedId entity = (IcadedId) o;
        return Objects.equals(this.lineno, entity.lineno) &&
                Objects.equals(this.adjenseq, entity.adjenseq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineno, adjenseq);
    }

}