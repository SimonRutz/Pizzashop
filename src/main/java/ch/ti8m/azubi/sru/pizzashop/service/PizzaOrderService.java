package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface PizzaOrderService {

    PizzaOrder createPizzaOrder(PizzaOrder pizzaOrder) throws IllegalArgumentException, SQLException;

    PizzaOrder getPizzaOrder(int pizzaID, int orderID) throws NoSuchElementException, SQLException;

    List<PizzaOrder> list() throws SQLException;

    void updatePizzaOrder(int order_id) throws IllegalArgumentException, SQLException;

    void deletePizzaOrder(int pizzaID, int orderID) throws SQLException;
}
