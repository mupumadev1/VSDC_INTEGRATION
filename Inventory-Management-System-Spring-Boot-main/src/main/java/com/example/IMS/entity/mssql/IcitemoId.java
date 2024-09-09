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
public class IcitemoId implements java.io.Serializable {
    private static final long serialVersionUID = 2943237676999944090L;
    @Column(name = "ITEMNO", nullable = false, length = 24)
    private String itemno;

    @Column(name = "OPTFIELD", nullable = false, length = 12)
    private String optfield;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IcitemoId entity = (IcitemoId) o;
        return Objects.equals(this.optfield, entity.optfield) &&
                Objects.equals(this.itemno, entity.itemno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optfield, itemno);
    }

}