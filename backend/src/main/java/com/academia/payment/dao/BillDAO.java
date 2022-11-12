package com.academia.payment.dao;

import com.academia.payment.bean.Bill;

import java.util.List;

public interface BillDAO {
    List<Bill> getBills(Integer student_id);
    void createBill(Bill bill);
    Boolean payBill(Integer billId);
}
