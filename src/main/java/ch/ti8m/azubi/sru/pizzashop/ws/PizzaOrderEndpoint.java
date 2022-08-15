package ch.ti8m.azubi.sru.pizzashop.ws;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.service.*;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@Path("/PizzaOrder")
public class PizzaOrderEndpoint {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    PizzaOrderServiceImpl pizzaOrderService = new PizzaOrderServiceImpl(dataBaseConnection.connection());
    OrderService orderService = new OrderServiceImpl(dataBaseConnection.connection());

    public PizzaOrderEndpoint() throws SQLException, ClassNotFoundException {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PizzaOrder> getPizzaOrderList() throws SQLException {

        List<PizzaOrder> returnList = new ArrayList<>();

        for (PizzaOrder pizzaOrder : pizzaOrderService.list()) {
            if (pizzaOrder.getOrderID() == 0) {
                returnList.add(pizzaOrder);
            }
        }

        return returnList;
    }

    @GET
    @Path("{pizza_ID},{order_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public PizzaOrder getPizzaOrder(@PathParam("pizza_ID") int pizza_ID, @PathParam("order_ID") int order_ID) throws SQLException {
        return pizzaOrderService.getPizzaOrder(pizza_ID, order_ID);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createPizzaOrder(PizzaOrder pizzaOrder) throws SQLException {

        pizzaOrderService.combinePizzaOrder(pizzaOrder);
    }

    @PUT
    public void finishPizzaOrder() throws SQLException {
        int order_id = orderService.list().size();
        pizzaOrderService.finishPizzaOrder(order_id);
    }

    @DELETE
    @Path("{pizza_ID},{order_ID}")
    public void deletePizzaOrder(@PathParam("pizza_ID") int pizza_ID, @PathParam("order_ID") int order_ID) throws SQLException {
        pizzaOrderService.deletePizzaOrder(pizza_ID, order_ID);
    }
}
