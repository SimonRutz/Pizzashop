package ch.ti8m.azubi.sru.pizzashop.ws;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaService;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;

import javax.inject.Named;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Named
@Path("/Pizza")
public class PizzaEndpoint {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    PizzaServiceImpl pizzaService = new PizzaServiceImpl(dataBaseConnection.connection());

    public PizzaEndpoint() throws SQLException, ClassNotFoundException {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pizza> getPizzaList() throws SQLException {
        return pizzaService.list();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza getPizza(@PathParam("id") int id) throws SQLException {
        return pizzaService.getPizza(id);
    }

    @GET
    @Path("/search/{searchBarValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pizza> getListBySearch(@PathParam("searchBarValue") String searchBarValue) throws SQLException {
        return pizzaService.findPizza(searchBarValue);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza createPizza(Pizza pizza) throws SQLException {

        return pizzaService.createPizza(pizza);
    }

    @PUT
    public void updatePizza(Pizza pizza) throws SQLException {
        pizzaService.updatePizza(pizza);
    }

    @DELETE
    @Path("{id}")
    public void deletePizza(@PathParam("id") int id) throws SQLException {
        pizzaService.deletePizza(id);
    }
}
