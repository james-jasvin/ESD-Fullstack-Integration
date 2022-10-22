package com.academia.payment.bean;

import com.academia.payment.bean.Bill;
import com.academia.payment.bean.Student;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receiptId;

    @ManyToOne
    @JoinColumn(name="billId", nullable = false)
    private Bill bill;

    @Column(nullable = false)
    private Integer amountPaid;

    @Column(nullable = false)
    private String dateOfPayment;

    public Receipt() {
    }

    public Receipt(Bill bill, Integer amountPaid, String dateOfPayment) {
        this.bill = bill;
        this.amountPaid = amountPaid;
        this.dateOfPayment = dateOfPayment;
    }

    @JsonbTransient
    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
}