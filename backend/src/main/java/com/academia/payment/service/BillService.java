package com.academia.payment.service;

import com.academia.payment.bean.Bill;
import com.academia.payment.bean.Receipt;
import com.academia.payment.bean.Student;
import com.academia.payment.dao.impl.BillDAOImpl;
import com.academia.payment.dao.impl.StudentDAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillService {
    BillDAOImpl billDAO = new BillDAOImpl();

    public List<Bill> getBills(Integer s_id){
        List<Bill> billList = billDAO.getBills(s_id);

        // Removing student attribute and receipt attributes from returned list to kill cyclic references
        for (Bill bill: billList) {
            bill.setStudent(null);
            bill.setReceiptList(null);
        }

        return billList;
    }

    public Integer payBills(HashMap<Integer, Receipt> paymentDictionary) {
        Integer successfulPayments = 0;

        for (Map.Entry<Integer, Receipt> keyValuePair: paymentDictionary.entrySet()) {
            Integer billId = keyValuePair.getKey();
            Receipt receipt = keyValuePair.getValue();

            successfulPayments += billDAO.payBills(billId, receipt);
        }

        return successfulPayments;
    }
    public boolean deleteBill(Integer billId)
    {
        return billDAO.deleteBill(billId);
    }
}