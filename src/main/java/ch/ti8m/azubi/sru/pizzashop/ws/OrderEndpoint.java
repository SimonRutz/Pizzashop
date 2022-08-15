package ch.ti8m.azubi.sru.pizzashop.ws;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.service.*;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Named
@Path("/Order")
public class OrderEndpoint {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    OrderServiceImpl orderService = new OrderServiceImpl(dataBaseConnection.connection());
    PizzaOrderServiceImpl pizzaOrderService = new PizzaOrderServiceImpl(dataBaseConnection.connection());

    public OrderEndpoint() throws SQLException, ClassNotFoundException {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrderList() throws SQLException {
        return orderService.list();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("id") int id) throws SQLException {
        return orderService.getOrder(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Order createOrder(Order order) throws SQLException{
        order.setOrderDateTime(new Timestamp(System.currentTimeMillis()));

        boolean emptyOrder = true;

        for (PizzaOrder pizzaOrder : pizzaOrderService.list()) {
            if (pizzaOrder.getOrderID() == 0) {
                emptyOrder = false;
                break;
            }
        }

        if (!emptyOrder) {
            orderService.createOrder(order);

            pizzaOrderService.finishPizzaOrder(order.getID());
        }

        return order;

    }

    @PUT
    public void updateOrder(Order order) throws SQLException {
        orderService.updateOrder(order);
    }


    @DELETE 
    @Path("{id}")
    public void deleteOrder(@PathParam("id") int id) throws SQLException {
        orderService.deleteOrder(id);
    }

}
