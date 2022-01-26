package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface OrderService {

    Order createOrder(Order order) throws IllegalArgumentException, SQLException;

    Order getOrder(int id) throws NoSuchElementException, SQLException;

    List<Order> list() throws SQLException;

    void updateOrder(Order order) throws IllegalArgumentException, SQLException;

    void deleteOrder(int id) throws SQLException;
}
