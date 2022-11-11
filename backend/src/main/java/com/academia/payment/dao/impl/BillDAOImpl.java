// TODO:
// FIX HQL query for Bill data foreign key something error
// Fetch logged in student's data from Student table, use student id to fetch Student's bills from bill table
// Send that as response to front-end
// Frontend will display a table with the bill data that was fetched
// Table can be hidden initially and only on successful login will the table's display be not none and filling will
// happen

package com.academia.payment.dao.impl;

import com.academia.payment.bean.Bill;
import com.academia.payment.dao.BillDAO;
import com.academia.payment.util.HibernateSessionUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {
    @Override
    public List<Bill> getBills(Integer s_id) {
        System.out.println(s_id);

        List<Bill> billList = new ArrayList<>();

        try (Session session = HibernateSessionUtil.getSession();){
            for (final Object bill : session.createQuery("FROM Bill WHERE student.studentId=:s_id AND amount > 0").setParameter("s_id", s_id).list()) {
                billList.add((Bill) bill);
            }
        }
        catch (HibernateException exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        return billList;
    }

    @Override
    public boolean payBill(Integer billId) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            String query_string="delete from Bill where billId=:billId";
            Query query=session.createQuery(query_string);
            query.setParameter("billId", billId);
            query.executeUpdate();
            transaction.commit();
            return true;

        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        return false;
    }

    // To fill in dummy data to initialize DB with
    @Override
    public void createBill(Bill bill) {

        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(bill);
            transaction.commit();

        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }
}