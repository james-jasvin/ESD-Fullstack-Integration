package com.academia.payment.util;

import com.academia.payment.bean.Bill;
import com.academia.payment.bean.Student;
import com.academia.payment.dao.BillDAO;
import com.academia.payment.dao.StudentDAO;
import com.academia.payment.dao.impl.BillDAOImpl;
import com.academia.payment.dao.impl.StudentDAOImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitializeDB {
    public static void main(String[] args) {

        List<List<String>> students = Arrays.asList(
                Arrays.asList("MT2021059", "Jasvin", "James", "jasvin.james@iiitb.ac.in", "1234"),
                Arrays.asList("MT2021146", "Thakur", "Devendrasingh", "devendrasingh.thakur@iiitb.ac.in", "1234"),
                Arrays.asList("MT2021034", "Chandrakant", "Sahu", "chandrakant.sahu@iiitb.ac.in", "1234"),
                Arrays.asList("MT2021133", "Shwetank", "Singh", "shwetank.singh@iiitb.ac.in", "1234"),
                Arrays.asList("MT2021047", "Gaurav", "Tirodkar", "gaurav.tirodkar@iiitb.ac.in", "1234")
        );

        List<List<Object>> bills = Arrays.asList(
                Arrays.asList("Yearly Fees", 69000, "2021-12-23"),
                Arrays.asList("Caution Deposit", 69000, "2021-12-23"),
                Arrays.asList("Miscellaneous Fees", 69000, "2021-12-23"),

                Arrays.asList("Yearly Fees", 420000, "2021-12-23"),
                Arrays.asList("Caution Deposit", 69000, "2021-12-23"),
                Arrays.asList("Miscellaneous Fees", 69000, "2021-12-23"),

                Arrays.asList("Yearly Fees", 420000, "2021-12-23"),
                Arrays.asList("Caution Deposit", 69000, "2021-12-23"),
                Arrays.asList("Miscellaneous Fees", 69000, "2021-12-23"),

                Arrays.asList("Yearly Fees", 420000, "2021-12-23"),
                Arrays.asList("Caution Deposit", 69000, "2021-12-23"),
                Arrays.asList("Miscellaneous Fees", 69000, "2021-12-23"),

                Arrays.asList("Yearly Fees", 420000, "2021-12-23"),
                Arrays.asList("Caution Deposit", 69000, "2021-12-23"),
                Arrays.asList("Miscellaneous Fees", 69000, "2021-12-23")
        );

        StudentDAO studentDAO = new StudentDAOImpl();
        BillDAO billDAO = new BillDAOImpl();

        List<Bill> billList = new ArrayList<>();

        for (List<Object> bill: bills) {
            Bill billObj = new Bill((String) bill.get(0), (Integer) bill.get(1), (String) bill.get(2));
            billList.add(billObj);
        }

        for (int i=0; i<students.size(); i++) {
            List<String> student = students.get(i);
            Student studentObj = new Student(student.get(0), student.get(1), student.get(2), student.get(3), student.get(4));

            // Set students of bills
            billList.get(i).setStudent(studentObj);
            billList.get(i+1).setStudent(studentObj);
            billList.get(i+2).setStudent(studentObj);

            // Set billList of student
            studentObj.setBillList(Arrays.asList(billList.get(i), billList.get(i+1), billList.get(i+2)));

            studentDAO.createStudent(studentObj);

            billDAO.createBill(billList.get(i));
            billDAO.createBill(billList.get(i+1));
            billDAO.createBill(billList.get(i+2));
        }
    }
}
