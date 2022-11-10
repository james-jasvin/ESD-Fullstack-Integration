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
    BillService billService=new BillService();

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON) //return type
    // @Consumes(MediaType.APPLICATION_JSON) //parameter
    public Response getBills(@QueryParam("studentId") int s_id) {
        List<Bill> billList = billService.getBills(s_id);

        return Response.ok().header("Access-Control-Allow-Origin","*").
                header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
        .entity(billList).build();
    }

    @POST
    @Path("/pay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response payBills(HashMap<Integer, Receipt> paymentDictionary) {
        Integer successfulPayments = billService.payBills(paymentDictionary);

        if (successfulPayments == paymentDictionary.size())
            return Response.ok().build();
        else
            // Return OK status code but 202 = There's no guarantee everything panned out as it should
            return Response.status(202).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBill(@QueryParam("billId")Integer  billId)
    {
        boolean isDeleted = billService.deleteBill(billId);
        if(isDeleted==true)
        {
            return Response.status(200).header("Access-Control-Allow-Origin","*").
                header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600").entity("Bill entry with Bill-id "+billId+" Deleted successfully").build();
        }
        else
        {
            return Response.status(417).entity("Operation Failed").build();
        }
    }


}