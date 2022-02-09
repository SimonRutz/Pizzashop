package ch.ti8m.azubi.sru.pizzashop.ws;

import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaOrderServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/PizzaOrder")
public class PizzaOrderEndpoint {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    PizzaOrderServiceImpl pizzaOrderService = new PizzaOrderServiceImpl(dataBaseConnection.connection());

    public PizzaOrderEndpoint() throws SQLException, ClassNotFoundException {
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PizzaOrder> getPizzaOrderList() throws SQLException {
        return pizzaOrderService.list();
    }

    @GET
    @Path("{pizza_ID},{order_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public PizzaOrder getPizzaOrder(@PathParam("pizza_ID") int pizza_ID, @PathParam("order_ID") int order_ID) throws SQLException {
        return pizzaOrderService.getPizzaOrder(pizza_ID, order_ID);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public PizzaOrder createPizzaOrder(int amount, int pizza_ID, int order_ID) throws SQLException {
        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setAmount(amount);
        pizzaOrder.setPizzaID(pizza_ID);
        pizzaOrder.setOrderID(order_ID);

        return pizzaOrderService.createPizzaOrder(pizzaOrder);
    }

    @PUT
    @Path("/")
    public void updatePizzaOrder(PizzaOrder pizzaOrder) throws SQLException {
        pizzaOrderService.updatePizzaOrder(pizzaOrder);
    }

    @DELETE
    @Path("/{pizza_ID},{order_ID}")
    public void deletePizzaOrder(@PathParam("pizza_ID") int pizza_ID, @PathParam("order_ID") int order_ID) throws SQLException {
        pizzaOrderService.deletePizzaOrder(pizza_ID, order_ID);
    }
}
