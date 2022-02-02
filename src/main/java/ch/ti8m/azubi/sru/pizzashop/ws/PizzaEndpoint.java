package ch.ti8m.azubi.sru.pizzashop.ws;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaOrderServerRegistry;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaService;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaServiceRegistry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/pizza")
public class PizzaEndpoint {

    private PizzaService pizzaService() {
        return PizzaServiceRegistry.getInstance().get(PizzaService.class);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pizza> getPizzaList() throws SQLException {
        return pizzaService().list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza getPizza(@PathParam("id") int id) throws SQLException {
        return pizzaService().getPizza(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza createPizza(String name, Double price) throws SQLException {
        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setPrice(price);

        return pizzaService().createPizza(pizza);
    }

    @PUT
    @Path("/")
    public void updatePizza(Pizza pizza) throws SQLException {
        pizzaService().updatePizza(pizza);
    }

    @DELETE
    @Path("/{id}")
    public void deletePizza(@PathParam("id") int id) throws SQLException {
         pizzaService().deletePizza(id);
    }
}
