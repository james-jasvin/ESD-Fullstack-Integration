package com.academia.payment.controller;

import com.academia.payment.bean.Student;
import com.academia.payment.service.StudentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("student")
public class StudentController {
    StudentService studentService = new StudentService();

    /*
        Path: POST /api/student/login
        Input: Student Object whose schema is defined in com.academia.payment.bean.Student
        Response: 200 (OK) + logged in Student object if login succeeded else 401 (Bad Request) with no body
    */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Student student) {
        Student loggedInStudent = studentService.login(student);

        if (loggedInStudent == null)
            return Response.status(401).build();
        else
            return Response.ok().entity(loggedInStudent).build();
    }
}