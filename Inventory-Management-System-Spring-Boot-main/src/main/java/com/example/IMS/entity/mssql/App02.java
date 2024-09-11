package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "APP02")
public class App02 {
    @Id
    @Column(name = "RECID02", nullable = false, length = 6)
    private String recid02;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "DATELASTMN", nullable = false, precision = 9)
    private BigDecimal datelastmn;

    @Column(name = "INVCBTCH", nullable = false, precision = 9)
    private BigDecimal invcbtch;

    @Column(name = "VCHRNBR", nullable = false, precision = 9)
    private BigDecimal vchrnbr;

    @Column(name = "SWALOWDISC", nullable = false)
    private Short swalowdisc;

    @Column(name = "SWUSE1099", nullable = false)
    private Short swuse1099;

    @Column(name = "CLASLABEL", nullable = false, length = 10)
    private String claslabel;

    @Column(name = "CNTNEXTINV", nullable = false, precision = 9)
    private BigDecimal cntnextinv;

    @Column(name = "SWCOAWARN", nullable = false)
    private Short swcoawarn;

    @Column(name = "SWPOSTPRNT", nullable = false)
    private Short swpostprnt;

    @Column(name = "SWALOWIVED", nullable = false)
    private Short swalowived;

    @Column(name = "SWEDIT1099", nullable = false)
    private Short swedit1099;

    @Column(name = "SW1099COPY", nullable = false)
    private Short sw1099copy;

    @Column(name = "SWDATEBUS", nullable = false)
    private Short swdatebus;

}