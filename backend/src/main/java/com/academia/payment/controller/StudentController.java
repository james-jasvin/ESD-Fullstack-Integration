package com.academia.payment.controller;

import com.academia.payment.bean.Student;
import com.academia.payment.service.StudentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Path("student")
public class StudentController {
    StudentService studentService = new StudentService();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Student student) {
        Student loggedInStudent = studentService.login(student);

        if (loggedInStudent == null)
            return Response.status(401).entity(null).build();
        else
            return Response.ok().entity(loggedInStudent).build();
    }
}