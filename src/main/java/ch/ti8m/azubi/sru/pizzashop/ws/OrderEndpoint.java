package ch.ti8m.azubi.sru.pizzashop.ws;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.service.OrderServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Path("/Order")
public class OrderEndpoint {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    OrderServiceImpl orderService = new OrderServiceImpl(dataBaseConnection.connection());

    public OrderEndpoint() throws SQLException, ClassNotFoundException {
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrderList() throws SQLException {
        return orderService.list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("id") int id) throws SQLException {
        return orderService.getOrder(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Order createOrder(Date orderDateTime, String phoneNumber, String address) throws SQLException{
        Order order = new Order(orderDateTime, phoneNumber, address);
        order.setOrderDateTime(orderDateTime);
        order.setPhoneNumber(phoneNumber);
        order.setAddress(address);

        return orderService.createOrder(order);
    }

    @PUT
    @Path("/")
    public void updateOrder(Order order) throws SQLException {
        orderService.updateOrder(order);
    }


    @DELETE 
    @Path("/{id}")
    public void deleteOrder(@PathParam("id") int id) throws SQLException {
        orderService.deleteOrder(id);
    }

}
