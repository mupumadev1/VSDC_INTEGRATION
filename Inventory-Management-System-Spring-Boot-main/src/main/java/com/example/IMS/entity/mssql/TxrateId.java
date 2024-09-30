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
public class TxrateId implements Serializable {
    private static final long serialVersionUID = 4351292366448863908L;
    @Column(name = "AUTHORITY", nullable = false, length = 12)
    private String authority;

    @Column(name = "TTYPE", nullable = false)
    private Short ttype;

    @Column(name = "BUYERCLASS", nullable = false)
    private Short buyerclass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TxrateId entity = (TxrateId) o;
        return Objects.equals(this.buyerclass, entity.buyerclass) &&
                Objects.equals(this.authority, entity.authority) &&
                Objects.equals(this.ttype, entity.ttype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerclass, authority, ttype);
    }

}