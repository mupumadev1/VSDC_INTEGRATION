package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "APIBS")
public class Apibs {
    @EmbeddedId
    private ApibId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "DATEDUE", nullable = false, precision = 9)
    private BigDecimal datedue;

    @Column(name = "AMTDUE", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdue;

    @Column(name = "DATEDISC", nullable = false, precision = 9)
    private BigDecimal datedisc;

    @Column(name = "AMTDISC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdisc;

    @Column(name = "AMTDUEHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtduehc;

    @Column(name = "AMTDISCHC", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtdischc;

}