package org.kainos.ea.resources;


import io.swagger.annotations.Api;
import org.kainos.ea.api.CustomerService;
import org.kainos.ea.api.OrderService;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.client.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Order")
@Path("/api")
public class CustomerController {
    private CustomerService customerService = new CustomerService();

    @GET
    @Path("/customers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        try {
            return Response.ok(customerService.getAllCustomers()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
