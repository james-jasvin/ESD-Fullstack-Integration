package com.academia.payment.dao;

import com.academia.payment.bean.Student;

public interface StudentDAO {
    Student login(Student student);
    void createStudent(Student student);
}
