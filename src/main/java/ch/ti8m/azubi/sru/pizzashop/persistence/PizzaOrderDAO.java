package ch.ti8m.azubi.sru.pizzashop.persistence;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class PizzaOrderDAO {

    private Connection connection;

    public PizzaOrderDAO() {}

    public PizzaOrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(PizzaOrder pizzaOrder) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("insert into pizza_ordering (amount, pizza_ID, orders_ID) values (?, ?, ?)")) {

            statement.setInt(1, pizzaOrder.getAmount());
            statement.setInt(2, pizzaOrder.getPizzaID());

            if (pizzaOrder.getOrderID() != null) {
                statement.setInt(3, pizzaOrder.getOrderID());
            } else {
                statement.setString(3, null);
            }

            statement.executeUpdate();
        }
    }

    public void combine(PizzaOrder pizzaOrder, PizzaOrder existingOrder) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("update pizza_ordering set amount = ? + ? where pizza_ID = ? and orders_ID is null;")) {

            statement.setInt(1, existingOrder.getAmount());
            statement.setInt(2, pizzaOrder.getAmount());
            statement.setInt(3, pizzaOrder.getPizzaID());

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

    public PizzaOrder get (int pizzaID, int orderID) throws SQLException {

        PizzaOrder returnPizzaOrder = new PizzaOrder();

        try(PreparedStatement statement = connection.prepareStatement("select amount, pizza_ID, orders_ID from pizza_ordering where pizza_ID = ? and orders_ID = ?")) {

            statement.setInt(1, pizzaID);
            statement.setInt(2, orderID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Integer amount = resultSet.getInt("amount");
                Integer pizza_ID = resultSet.getInt("pizza_ID");
                Integer orders_ID = resultSet.getInt("orders_ID");

                PizzaDAO pizzaDAO = new PizzaDAO(connection);
                returnPizzaOrder = new PizzaOrder(amount, pizzaDAO.get(pizza_ID), orders_ID);
            }
        }
        return returnPizzaOrder;
    }

    public PizzaOrder getExistingOrder(int pizzaID) throws NoSuchElementException, SQLException {

        PizzaOrder returnPizzaOrder = new PizzaOrder();

        try(PreparedStatement statement = connection.prepareStatement("select amount, pizza_ID, orders_ID from pizza_ordering where pizza_ID = ? and orders_ID IS NULL")) {

            statement.setInt(1, pizzaID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Integer amount = resultSet.getInt("amount");
                Integer pizza_ID = resultSet.getInt("pizza_ID");
                Integer orders_ID = resultSet.getInt("orders_ID");

                PizzaDAO pizzaDAO = new PizzaDAO(connection);
                returnPizzaOrder = new PizzaOrder(amount, pizzaDAO.get(pizza_ID), orders_ID);
            }
        }
        return returnPizzaOrder;
    }

    public void update (PizzaOrder pizzaOrder) throws SQLException{

        try(PreparedStatement statement = connection.prepareStatement("update pizza_ordering set amount = ? where pizza_ID = ? and orders_ID = ?")) {

            statement.setInt(1, pizzaOrder.getAmount());
            statement.setInt(2, pizzaOrder.getPizzaID());
            statement.setInt(3, pizzaOrder.getOrderID());

            statement.executeUpdate();
        }
    }

    public void finishOrder (int order_id) throws  SQLException {

        try(PreparedStatement statement = connection.prepareStatement("update pizza_ordering set orders_ID = ? where orders_ID IS NULL")) {

            statement.setInt(1, order_id);

            statement.executeUpdate();
        }
    }

    public void delete (int pizzaID, int orderID) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("delete from pizza_ordering where pizza_ID = ? and orders_ID = ?")) {

            statement.setInt(1, pizzaID);
            statement.setInt(2, orderID);

            statement.executeUpdate();
        }
    }

    public void deleteExistingOrder (int pizzaID) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("delete from pizza_ordering where pizza_ID = ? and orders_ID IS NULL")) {

            statement.setInt(1, pizzaID);

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
