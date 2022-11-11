package com.academia.payment.service;

import com.academia.payment.bean.Bill;
import com.academia.payment.dao.impl.BillDAOImpl;

import java.util.List;

public class BillService {
    BillDAOImpl billDAO = new BillDAOImpl();

    public List<Bill> getBills(Integer s_id){
        List<Bill> billList = billDAO.getBills(s_id);

        // Removing student attribute from returned list to kill cyclic references
        for (Bill bill: billList)
            bill.setStudent(null);

        return billList;
    }
    public boolean payBill(Integer billId) {
        return billDAO.payBill(billId);
    }
}