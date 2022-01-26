package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzaServiceImpl implements PizzaService{

    PizzaDAO pizzaDAO = new PizzaDAO();

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
}
