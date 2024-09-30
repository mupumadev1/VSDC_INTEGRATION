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
@Table(name = "APIBC")
public class Apibc {
    @Id
    @Column(name = "CNTBTCH", nullable = false, precision = 9)
    private BigDecimal id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "DATEBTCH", nullable = false, precision = 9)
    private BigDecimal datebtch;

    @Column(name = "BTCHDESC", nullable = false, length = 60)
    private String btchdesc;

    @Column(name = "CNTINVCENT", nullable = false, precision = 7)
    private BigDecimal cntinvcent;

    @Column(name = "AMTENTR", nullable = false, precision = 19, scale = 3)
    private BigDecimal amtentr;

    @Column(name = "BTCHTYPE", nullable = false)
    private Short btchtype;

    @Column(name = "BTCHSTTS", nullable = false)
    private Short btchstts;

    @Column(name = "INVCTYPE", nullable = false)
    private Short invctype;

    @Column(name = "CNTLSTITEM", nullable = false, precision = 7)
    private BigDecimal cntlstitem;

    @Column(name = "POSTSEQNBR", nullable = false, precision = 9)
    private BigDecimal postseqnbr;

    @Column(name = "NBRERRORS", nullable = false, precision = 9)
    private BigDecimal nbrerrors;

    @Column(name = "DTELSTEDIT", nullable = false, precision = 9)
    private BigDecimal dtelstedit;

    @Column(name = "SWPRINTED", nullable = false)
    private Short swprinted;

    @Column(name = "SRCEAPPL", nullable = false, length = 2)
    private String srceappl;

    @Column(name = "SWICT", nullable = false)
    private Short swict;

}