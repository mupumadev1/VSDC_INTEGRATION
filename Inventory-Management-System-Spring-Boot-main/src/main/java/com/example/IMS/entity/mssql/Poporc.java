package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "POPORC")
public class Poporc {
    @EmbeddedId
    private PoporcId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "PORCSEQ", nullable = false, precision = 19)
    private BigDecimal porcseq;

    @Column(name = "INDBTABLE", nullable = false)
    private Short indbtable;

    @Column(name = "COMMENTTYP", nullable = false)
    private Short commenttyp;

    @Column(name = "COMMENT", nullable = false, length = 80)
    private String comment;

}