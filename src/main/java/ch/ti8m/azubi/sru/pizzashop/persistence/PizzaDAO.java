package ch.ti8m.azubi.sru.pizzashop.persistence;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class PizzaDAO {

    private Connection connection;

    public PizzaDAO () {}

    public PizzaDAO (Connection connection) {
        this.connection = connection;
    }

    public void create(Pizza pizza) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("insert into pizza (name, price) values (?, ?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, pizza.getName());
            statement.setDouble(2, pizza.getPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int generatedID = generatedKeys.getInt(1);

            pizza.setID(generatedID);
        }
    }

    public List<Pizza> list () throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("select id, name, price from pizza")) {

            List<Pizza> pizzaList = new LinkedList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Pizza pizza = new Pizza();
                pizza.setID(resultSet.getInt("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getDouble("price"));

                pizzaList.add(pizza);
            }
            return pizzaList;
        }
    }

    public Pizza get (int id) throws SQLException {

        Pizza returnPizza = null;

        try(PreparedStatement statement = connection.prepareStatement("select name, price from pizza where id = ?")) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                returnPizza = new Pizza(id, name, price);


            }
        }
        return returnPizza;
    }

    public void update (Pizza pizza) throws  SQLException {

        try(PreparedStatement statement = connection.prepareStatement("update pizza set name = ?, price = ? where id = ?")) {

            statement.setString(1, pizza.getName());
            statement.setDouble(2, pizza.getPrice());
            statement.setInt(3, pizza.getID());

            statement.executeUpdate();
        }
    }

    public void delete (int id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("delete pizza where id = ?")) {

            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public void save (Pizza pizza) throws SQLException {

        if (pizza.getID() == null) {
            create(pizza);
        } else {
            update(pizza);
        }
    }

    public List<Pizza> find (String searchText) throws SQLException {

        List<Pizza> returnList = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from pizza p where lower(p.name) like lower(?) or lower(p.price) like lower (?)")) {

            try {
                statement.setString(1, searchText);
                statement.setDouble(2, Double.parseDouble(searchText));
            } catch (NumberFormatException e) {
                statement.setString(1, searchText);
                statement.setString(2, searchText);
            }


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");

                returnList.add(new Pizza(id, name, price));
            }
        }
        return returnList;
    }
}
