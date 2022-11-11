package com.academia.payment.controller;

import com.academia.payment.bean.Bill;
import com.academia.payment.service.BillService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("bill")
public class BillController {
    BillService billService = new BillService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBills(@QueryParam("studentId") int s_id) {
        List<Bill> billList = billService.getBills(s_id);
        return Response.ok().entity(billList).build();
    }

    @DELETE
    @Path("/{billId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBill(@PathParam("billId") Integer billId) {
        boolean isDeleted = billService.payBill(billId);
        if (isDeleted)
            return Response.status(204).build();
        else
            return Response.status(400).entity("Operation Failed").build();
    }
}