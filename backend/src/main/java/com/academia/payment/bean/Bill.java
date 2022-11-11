package com.academia.payment.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String billDate;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    public Bill() {
    }

    public Bill(Integer billId, String description, Integer amount, String billDate) {
        this.billId = billId;
        this.description = description;
        this.amount = amount;
        this.billDate = billDate;
    }

    public Bill(String description, Integer amount, String billDate) {
        this.description = description;
        this.amount = amount;
        this.billDate = billDate;
    }

    public Bill(Integer billId, String description, Integer amount, String billDate, Student student) {
        this.billId = billId;
        this.description = description;
        this.amount = amount;
        this.billDate = billDate;
        this.student = student;
    }

    public Integer getBillId() {
        return billId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}