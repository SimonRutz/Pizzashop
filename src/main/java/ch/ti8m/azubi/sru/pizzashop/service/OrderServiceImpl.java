package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.persistence.OrderDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO = new OrderDAO();

    @Override
    public Order createOrder(Order order) throws IllegalArgumentException, SQLException {

        if(order == null) {
            throw new IllegalArgumentException("Order is required for creation");
        }

        orderDAO.create(order);

        return order;
    }

    @Override
    public Order getOrder(int id) throws NoSuchElementException, SQLException {

        Order order = orderDAO.get(id);

        if (order == null) {
            throw new NoSuchElementException("Order with this ID doesn't exist");
        }

        return order;
    }

    @Override
    public List<Order> list() throws SQLException {

        return orderDAO.list();
    }

    @Override
    public void updateOrder(Order order) throws IllegalArgumentException, SQLException {

        if (order == null) {
            throw new IllegalArgumentException("Order is required for update");
        }

        orderDAO.update(order);
    }

    @Override
    public void deleteOrder(int id) throws SQLException {

        orderDAO.delete(id);
    }
}
