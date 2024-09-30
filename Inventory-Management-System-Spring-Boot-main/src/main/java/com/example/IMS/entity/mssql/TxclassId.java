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
public class TxclassId implements Serializable {
    private static final long serialVersionUID = -7224510357038144093L;
    @Column(name = "AUTHORITY", nullable = false, length = 12)
    private String authority;

    @Column(name = "CLASSTYPE", nullable = false)
    private Short classtype;

    @Column(name = "CLASSAXIS", nullable = false)
    private Short classaxis;

    @Column(name = "CLASS", nullable = false)
    private Short classField;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TxclassId entity = (TxclassId) o;
        return Objects.equals(this.authority, entity.authority) &&
                Objects.equals(this.classtype, entity.classtype) &&
                Objects.equals(this.classaxis, entity.classaxis) &&
                Objects.equals(this.classField, entity.classField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority, classtype, classaxis, classField);
    }

}