package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface PizzaService {

    Pizza createPizza(Pizza pizza) throws IllegalArgumentException, SQLException;

    Pizza getPizza(int id) throws NoSuchElementException, SQLException;

    List<Pizza> list() throws SQLException;

    void updatePizza(Pizza pizza) throws IllegalArgumentException, SQLException;

    void deletePizza(int id) throws SQLException;

    List<Pizza> findPizza(String searchText) throws SQLException;
}
