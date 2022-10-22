package com.academia.payment.service;

import com.academia.payment.bean.Student;
import com.academia.payment.dao.impl.StudentDAOImpl;

public class StudentService {
    StudentDAOImpl studentDAO = new StudentDAOImpl();

    public Student login(Student student){
        Student loggedInStudent = studentDAO.login(student);

        // If no login happens, then return null
        if (loggedInStudent == null)
            return null;

        // Setting billList to null to avoid cyclic dependency issues
        loggedInStudent.setBillList(null);

        return loggedInStudent;
    }
}