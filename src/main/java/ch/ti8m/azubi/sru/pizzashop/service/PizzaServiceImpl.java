package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaOrderDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzaServiceImpl implements PizzaService{

    private Connection connection;
    private PizzaDAO pizzaDAO;

    public PizzaServiceImpl() {
    }

    public PizzaServiceImpl(Connection connection) {
        this.connection = connection;
        pizzaDAO = new PizzaDAO(connection);
    }

    @Override
    public Pizza createPizza(Pizza pizza) throws IllegalArgumentException, SQLException {

        if (pizza == null) {
            throw new IllegalArgumentException("Pizza is required for creation");
        }

        pizzaDAO.create(pizza);
        return pizza;
    }

    @Override
    public Pizza getPizza(int id) throws NoSuchElementException, SQLException {

        Pizza pizza = pizzaDAO.get(id);

        if (pizza == null) {
            throw new NoSuchElementException("Pizza with this ID doesn't exist");
        }

        return pizza;
    }

    @Override
    public List<Pizza> list() throws SQLException {

        return pizzaDAO.list();
    }

    @Override
    public void updatePizza(Pizza pizza) throws IllegalArgumentException, SQLException {

        if (pizza == null) {
            throw new IllegalArgumentException("Pizza is required for update");
        }

        pizzaDAO.update(pizza);
    }

    @Override
    public void deletePizza(int id) throws SQLException {

        pizzaDAO.delete(id);
    }

    @Override
    public List<Pizza> findPizza(String searchText) throws SQLException {

        return pizzaDAO.find(searchText);
    }
}
