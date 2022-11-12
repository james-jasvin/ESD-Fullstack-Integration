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
    /*
        This method performs login of the Student object specified
        So it basically checks whether the credentials provided in the Student object match with
        a Student object in the database
    */
    @Override
    public Student login(Student student) {
        try (Session session = HibernateSessionUtil.getSession();){
            String studentEmail = student.getEmail();
            String studentPassword = student.getPassword();

            List<Object> result = new ArrayList<Object>(
                session.createQuery(
                        "FROM Student WHERE email = :studentEmail and password = :studentPassword"
                        )
                        .setParameter("studentEmail", studentEmail)
                        .setParameter("studentPassword", studentPassword)
                        .list()
            );

            // If no valid Student found, return null so that login failure is understood
            if (result.size() == 0)
                return null;
            else
                return (Student) result.get(0);
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }

        return null;
    }

    /*
        This method inserts the provided Student object into the database
        Used by the InitializeDB script to initialize the database with dummy data
    */
    @Override
    public void createStudent(Student student) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }
}