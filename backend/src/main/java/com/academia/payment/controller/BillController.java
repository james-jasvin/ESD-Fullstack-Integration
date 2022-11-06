package com.academia.payment.controller;

import com.academia.payment.bean.Bill;
import com.academia.payment.bean.Receipt;
import com.academia.payment.bean.Student;
import com.academia.payment.service.BillService;
import com.academia.payment.service.StudentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@Path("bill")
public class BillController {
    @GET
    @Path("/get/{studentId}")
    @Produces(MediaType.APPLICATION_JSON) //return type
    // @Consumes(MediaType.APPLICATION_JSON) //parameter
    public Response getBills(@PathParam("studentId") int s_id) {
        List<Bill> billList = new BillService().getBills(s_id);

        return Response.ok().entity(billList).build();
    }

    @POST
    @Path("/pay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response payBills(HashMap<Integer, Receipt> paymentDictionary) {
        Integer successfulPayments = new BillService().payBills(paymentDictionary);

        if (successfulPayments == paymentDictionary.size())
            return Response.ok().build();
        else
            // Return OK status code but 202 = There's no guarantee everything panned out as it should
            return Response.status(202).build();
    }

}