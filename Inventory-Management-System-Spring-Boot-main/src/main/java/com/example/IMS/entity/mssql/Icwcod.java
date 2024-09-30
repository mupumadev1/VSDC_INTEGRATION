package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ICWCOD")
public class Icwcod {
    @Id
    @Column(name = "WEIGHTUNIT", nullable = false, length = 10)
    private String weightunit;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "WUOMDESC", nullable = false, length = 60)
    private String wuomdesc;

    @Column(name = "CONVERSION", nullable = false, precision = 19, scale = 6)
    private BigDecimal conversion;

}