package com.academia.payment.controller;

import com.academia.payment.bean.Student;
import com.academia.payment.service.StudentService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("student")
public class StudentController {
    StudentService studentService = new StudentService();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON) //return type
    @Consumes(MediaType.APPLICATION_JSON) //parameter
    public Response login(Student student) {
        Student loggedInStudent = studentService.login(student);

        if (loggedInStudent == null)
            return Response.status(401).entity(null).build();
        else
            return Response.ok().entity(loggedInStudent).build();
    }
}