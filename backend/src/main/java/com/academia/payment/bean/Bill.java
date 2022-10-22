package com.academia.payment.bean;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private List<Receipt> receiptList;

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

    @JsonbTransient
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @JsonbTransient
    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }
}