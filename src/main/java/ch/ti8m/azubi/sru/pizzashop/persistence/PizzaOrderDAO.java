package ch.ti8m.azubi.sru.pizzashop.persistence;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PizzaOrderDAO {

    private Connection connection;

    public PizzaOrderDAO() {}

    public PizzaOrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(PizzaOrder pizzaOrder) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("insert into pizza_ordering (amount, pizza_ID, orders_ID) values (?, ?, ?)")){

            statement.setInt(1, pizzaOrder.getAmount());
            statement.setInt(2, pizzaOrder.getPizzaID());
            statement.setInt(3, pizzaOrder.getOrderID());
            statement.executeUpdate();
        }
    }

    public List<PizzaOrder> list () throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("select amount, pizza_ID, orders_ID from pizza_ordering")) {

            List<PizzaOrder> pizzaOrderList = new LinkedList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PizzaOrder pizzaOrder = new PizzaOrder();
                pizzaOrder.setAmount(resultSet.getInt("amount"));
                pizzaOrder.setPizzaID(resultSet.getInt("pizza_ID"));
                pizzaOrder.setOrderID(resultSet.getInt("orders_ID"));

                PizzaDAO pizzaDAO = new PizzaDAO(connection);
                pizzaOrder.setPizza(pizzaDAO.get(pizzaOrder.getPizzaID()));

                pizzaOrderList.add(pizzaOrder);
            }
            return pizzaOrderList;
        }
    }

    public List<PizzaOrder> getByOrderID (int id) throws SQLException {

        List<PizzaOrder> returnPizzaOrder = new LinkedList<>();

        try(PreparedStatement statement = connection.prepareStatement("select amount, pizza_ID, orders_ID from pizza_ordering where orders_ID = ?")) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Integer amount = resultSet.getInt("amount");
                Integer pizzaID = resultSet.getInt("pizza_ID");
                Integer ordersId = resultSet.getInt("orders_ID");

                PizzaDAO pizzaDAO = new PizzaDAO(connection);
                returnPizzaOrder.add(new PizzaOrder(amount, pizzaDAO.get(pizzaID), ordersId));
            }
        }
        return returnPizzaOrder;
    }

    public void update (PizzaOrder pizzaOrder) throws  SQLException {

        try(PreparedStatement statement = connection.prepareStatement("update pizza_ordering set amount = ? where pizza_ID = ? and orders_ID = ?")) {

            statement.setInt(1, pizzaOrder.getAmount());
            statement.setInt(2, pizzaOrder.getPizzaID());
            statement.setInt(3, pizzaOrder.getOrderID());

            statement.executeUpdate();
        }
    }

    public void delete (int pizzaID, int orderID) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("delete pizza_ordering where pizza_ID = ? and orders_ID = ?")) {

            statement.setInt(1, pizzaID);
            statement.setInt(2, orderID);

            statement.executeUpdate();
        }
    }

    public List<PizzaOrder> find (int search) throws SQLException {

        List<PizzaOrder> returnPizzaOrder = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from pizza_ordering po where po.amount = ?" +
                "or po.pizza_ID = (?) " +
                "or po.orders_ID = (?)")) {

            statement.setInt(1, search);
            statement.setInt(2, search);
            statement.setInt(3, search);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Integer amount = resultSet.getInt("amount");
                Integer pizzaID = resultSet.getInt("pizza_ID");
                Integer ordersId = resultSet.getInt("orders_ID");

                PizzaDAO pizzaDAO = new PizzaDAO(connection);
                returnPizzaOrder.add(new PizzaOrder(amount, pizzaDAO.get(pizzaID), ordersId));
            }
        }
        return returnPizzaOrder;
    }
}