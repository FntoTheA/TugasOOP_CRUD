package com.aritmatika.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "hasil_aritmatika")
public class HasilAritmatika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bilangan1", nullable = false)
    private Double bilangan1;

    @Column(name = "operator", nullable = false, length = 10)
    private String operator;

    @Column(name = "bilangan2", nullable = false)
    private Double bilangan2;

    @Column(name = "result")
    private Double result;

    public HasilAritmatika() {
    }

    public HasilAritmatika(Double bilangan1, String operator, Double bilangan2) {
        this.bilangan1 = bilangan1;
        this.operator = operator;
        this.bilangan2 = bilangan2;
        this.result = calculateResult(bilangan1, operator, bilangan2);
    }

    @PrePersist
    @PreUpdate
    public void computeResultBeforeSave() {
        this.result = calculateResult(this.bilangan1, this.operator, this.bilangan2);
    }

    public static Double calculateResult(Double b1, String op, Double b2) {
        if (b1 == null || b2 == null || op == null) {
            return null;
        }
        return switch (op.trim()) {
            case "+" -> b1 + b2;
            case "-" -> b1 - b2;
            case "*", "×" -> b1 * b2;
            case "/", "÷" -> {
                if (b2 == 0.0) {
                    throw new ArithmeticException("Tidak dapat melakukan pembagian dengan nol (0).");
                }
                yield b1 / b2;
            }
            default -> throw new IllegalArgumentException("Operator tidak valid: " + op);
        };
    }

    public String getFormattedBilangan1() {
        return formatNumber(this.bilangan1);
    }

    public String getFormattedBilangan2() {
        return formatNumber(this.bilangan2);
    }

    public String getFormattedResult() {
        return formatNumber(this.result);
    }

    public static String formatNumber(Double value) {
        if (value == null) {
            return "-";
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return "Tak Terdefinisi";
        }
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf(value.longValue());
        }
        return BigDecimal.valueOf(value)
                .setScale(6, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBilangan1() {
        return bilangan1;
    }

    public void setBilangan1(Double bilangan1) {
        this.bilangan1 = bilangan1;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getBilangan2() {
        return bilangan2;
    }

    public void setBilangan2(Double bilangan2) {
        this.bilangan2 = bilangan2;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}

