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
@Table(name = "TXRATE")
public class Txrate {
    @EmbeddedId
    private TxrateId id;

    @Column(name = "AUDTDATE", nullable = false, precision = 9)
    private BigDecimal audtdate;

    @Column(name = "AUDTTIME", nullable = false, precision = 9)
    private BigDecimal audttime;

    @Column(name = "AUDTUSER", nullable = false, length = 8)
    private String audtuser;

    @Column(name = "AUDTORG", nullable = false, length = 6)
    private String audtorg;

    @Column(name = "ITEMRATE1", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate1;

    @Column(name = "ITEMRATE2", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate2;

    @Column(name = "ITEMRATE3", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate3;

    @Column(name = "ITEMRATE4", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate4;

    @Column(name = "ITEMRATE5", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate5;

    @Column(name = "ITEMRATE6", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate6;

    @Column(name = "ITEMRATE7", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate7;

    @Column(name = "ITEMRATE8", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate8;

    @Column(name = "ITEMRATE9", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate9;

    @Column(name = "ITEMRATE10", nullable = false, precision = 15, scale = 5)
    private BigDecimal itemrate10;

    @Column(name = "LASTMAINT", nullable = false, precision = 9)
    private BigDecimal lastmaint;

}