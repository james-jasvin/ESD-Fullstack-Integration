package com.academia.payment.dao;

import com.academia.payment.bean.Bill;
import com.academia.payment.bean.Receipt;

import java.util.List;

public interface BillDAO {
    List<Bill> getBills(Integer student_id);
    void createBill(Bill bill);
    Integer payBills(Integer billId, Receipt receipt);
    boolean deleteBill(Integer billId);
}
