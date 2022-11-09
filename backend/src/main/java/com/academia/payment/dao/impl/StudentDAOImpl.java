package com.academia.payment.dao.impl;

import com.academia.payment.util.HibernateSessionUtil;
import com.academia.payment.dao.StudentDAO;
import com.academia.payment.bean.Student;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public Student login(Student student) {
        try (Session session = HibernateSessionUtil.getSession();){
            String s_email = student.getEmail();
            String s_password = student.getPassword();

            System.out.println(s_email);
            System.out.println(s_password);


            List<Object> result = new ArrayList<Object>(
                session.createQuery(
                        "FROM Student WHERE email = :s_email and password = :s_password"
                        )
                        .setParameter("s_email", s_email)
                        .setParameter("s_password", s_password)
                        .list()
            );

            if (result.size() == 0)
                return null;
            else
                return (Student) result.get(0);

        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }

        return null;
    }

    // To fill table with dummy data to initialize DB with
    @Override
    public void createStudent(Student student) {

        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();

        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }


}