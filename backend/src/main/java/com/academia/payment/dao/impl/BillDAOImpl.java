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
    /*
        This method fetches all the Bills of the Students with ID = s_id
    */
    @Override
    public List<Bill> getBills(Integer s_id) {
        List<Bill> billList = new ArrayList<>();

        try (Session session = HibernateSessionUtil.getSession()){
            for (
                    final Object bill : session
                    .createQuery("FROM Bill WHERE student.studentId=:s_id AND amount > 0")
                    .setParameter("s_id", s_id).list()
            )
                billList.add((Bill) bill);
        }
        catch (HibernateException exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        return billList;
    }

    /*
        This method "pays" the Bill with ID = billId
        So it basically deletes the Bill with the given ID
    */
    @Override
    public Boolean payBill(Integer billId) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            String query_string="DELETE FROM Bill WHERE billId=:billId";
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

    /*
        This method inserts the given Bill object into the Database
        Not used by any Controller but it is used by the InitializeDB script to initialize the
        database with dummy data
    */
    @Override
    public void createBill(Bill bill) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(bill);
            transaction.commit();
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }
}