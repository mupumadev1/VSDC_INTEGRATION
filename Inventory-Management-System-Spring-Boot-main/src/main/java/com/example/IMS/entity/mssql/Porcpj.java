package com.example.IMS.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "PORCPJ", schema = "dbo")
public class Porcpj {
    @Id
    @Column(name = "RCPHSEQ", nullable = false, precision = 19)
    private BigDecimal id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    public Porcpj(BigDecimal id, BigDecimal audtdate, BigDecimal audttime, String audtuser, String audtorg, BigDecimal prorateseq, BigDecimal postdate, BigDecimal scamount, BigDecimal fcamount, BigDecimal scdoctotal, Short iscomplete, BigDecimal dtcomplete, BigDecimal scrtgamt, BigDecimal caxamount) {
        this.id = id;
        this.audtdate = audtdate;
        this.audttime = audttime;
        this.audtuser = audtuser;
        this.audtorg = audtorg;
        this.prorateseq = prorateseq;
        this.postdate = postdate;
        this.scamount = scamount;
        this.fcamount = fcamount;
        this.scdoctotal = scdoctotal;
        this.iscomplete = iscomplete;
        this.dtcomplete = dtcomplete;
        this.scrtgamt = scrtgamt;
        this.caxamount = caxamount;
    }

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "PRORATESEQ", nullable = false, precision = 19)
    private BigDecimal prorateseq;

    @Column(name = "POSTDATE", nullable = false, precision = 9)
    private BigDecimal postdate;

    @Column(name = "SCAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scamount;

    @Column(name = "FCAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal fcamount;

    @Column(name = "SCDOCTOTAL", nullable = false, precision = 19, scale = 3)
    private BigDecimal scdoctotal;

    @Column(name = "ISCOMPLETE", nullable = false)
    private Short iscomplete;

    @Column(name = "DTCOMPLETE", nullable = false, precision = 9)
    private BigDecimal dtcomplete;

    @Column(name = "SCRTGAMT", nullable = false, precision = 19, scale = 3)
    private BigDecimal scrtgamt;

    @Column(name = "CAXAMOUNT", nullable = false, precision = 19, scale = 3)
    private BigDecimal caxamount;

}