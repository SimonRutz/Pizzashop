package ch.ti8m.azubi.sru.pizzashop.persistence;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO {

    private Connection connection;

    public OrderDAO () {}

    public OrderDAO (Connection connection) {
        this.connection = connection;
    }

    public void create(Order order) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("insert into orders (orderDateTime, phoneNumber, address) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setTimestamp(1, order.getOrderDateTime());
            statement.setString(2, order.getPhoneNumber());
            statement.setString(3, order.getAddress());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int generatedID = generatedKeys.getInt(1);

            order.setID(generatedID);
        }
    }

    public List<Order> list () throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("select id, orderDateTime, phoneNumber, address from orders")) {

            List<Order> orderList = new LinkedList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Order order = new Order();
                order.setID(resultSet.getInt("id"));
                order.setOrderDateTime(resultSet.getTimestamp("orderDateTime"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setAddress(resultSet.getString("address"));

                orderList.add(order);
            }
            return orderList;
        }
    }

    public Order get (int id) throws SQLException {

        Order returnOrder = null;

        try(PreparedStatement statement = connection.prepareStatement("select orderDateTime, phoneNumber, address from orders where id = ?")) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Timestamp orderDateTime = resultSet.getTimestamp("orderDateTime");
                String phoneNumber = resultSet.getString("phoneNumber");
                String address = resultSet.getString("address");
                returnOrder = new Order(id, orderDateTime, phoneNumber, address);
            }
        }
        return returnOrder;
    }

    public void update (Order order) throws  SQLException {

        try(PreparedStatement statement = connection.prepareStatement("update orders set orderDateTime = ?, phoneNumber = ?, address = ? where id = ?")) {

            statement.setTimestamp(1, order.getOrderDateTime());
            statement.setString(2, order.getPhoneNumber());
            statement.setString(3, order.getAddress());
            statement.setInt(4, order.getID());

            statement.executeUpdate();
        }
    }

    public void delete (int id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("delete orders where id = ?")) {

            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public void save (Order order) throws SQLException {

        if (order.getID() == null) {
            create(order);
        } else {
            update(order);
        }
    }

    public List<Order> find (String searchText) throws SQLException {

        List<Order> returnList = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from orders o where lower(o.orderDateTime) like lower(?)" +
                                                                        " or lower(o.phoneNumber) like lower (?) " +
                                                                        "or lower(o.address) like lower (?)")) {

            try {
                statement.setDate(1, Date.valueOf(searchText));
                statement.setString(2, searchText);
                statement.setString(3, searchText);
            } catch (IllegalArgumentException e) {
                statement.setString(1, searchText);
                statement.setString(2, searchText);
                statement.setString(3, searchText);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Integer id = resultSet.getInt("id");
                Timestamp orderDateTime = resultSet.getTimestamp("orderDateTime");
                String phoneNumber = resultSet.getString("phoneNumber");
                String address = resultSet.getString("address");

                returnList.add(new Order(id, orderDateTime, phoneNumber, address));
            }
        }
        return returnList;
    }
}
