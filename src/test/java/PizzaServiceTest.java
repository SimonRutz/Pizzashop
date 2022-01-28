import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.persistence.OrderDAO;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;
import ch.ti8m.azubi.sru.pizzashop.service.OrderServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PizzaServiceTest {

    @BeforeEach
    public void setup() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {

            try (Statement statement = connection.createStatement()) {
                statement.execute("drop table if exists pizza");
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("create table pizza ( \n" +
                        "id int auto_increment, \n" +
                        "name varchar(30) not null, \n" +
                        "price double not null, \n" +
                        "primary key (id))"
                );
            }
        }
    }

    @Test
    public void create() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaServiceImpl testService = new PizzaServiceImpl(connection);

            Pizza pizza = new Pizza("Salami", 12.99);
            testService.createPizza(pizza);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            Assertions.assertEquals(pizza, pizzaDAO.get(1));
        }
    }

    @Test
    public void get() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaServiceImpl testService = new PizzaServiceImpl(connection);

            Pizza pizza1 = new Pizza("Salami", 12.99);
            Pizza pizza2 = new Pizza("Prosciutto", 12.99);
            Pizza pizza3 = new Pizza("Margherita", 10.65);

            testService.createPizza(pizza1);
            testService.createPizza(pizza2);
            testService.createPizza(pizza3);

            Assertions.assertEquals(pizza2, testService.getPizza(2));
        }

    }

    @Test
    public void list() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaServiceImpl testService = new PizzaServiceImpl(connection);

            Pizza pizza1 = new Pizza("Salami", 12.99);
            Pizza pizza2 = new Pizza("Prosciutto", 12.99);
            Pizza pizza3 = new Pizza("Margherita", 10.65);

            testService.createPizza(pizza1);
            testService.createPizza(pizza2);
            testService.createPizza(pizza3);

            List<Pizza> expectedList = new LinkedList<>();
            expectedList.add(pizza1);
            expectedList.add(pizza2);
            expectedList.add(pizza3);

            Assertions.assertEquals(expectedList, testService.list());
        }

    }

    @Test
    public void update() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaServiceImpl testService = new PizzaServiceImpl(connection);

            Pizza pizza1 = new Pizza("Salami", 12.99);
            Pizza pizza2 = new Pizza("Prosciutto", 12.99);
            Pizza pizza3 = new Pizza("Margherita", 10.65);

            testService.createPizza(pizza1);
            testService.createPizza(pizza2);
            testService.createPizza(pizza3);

            Pizza updatePizza = new Pizza(2, "Prosciutto", 14.00);

            Assertions.assertEquals(pizza2, testService.getPizza(2));

            testService.updatePizza(updatePizza);

            Assertions.assertEquals(updatePizza, testService.getPizza(2));
        }
    }

    @Test
    public void delete() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaServiceImpl testService = new PizzaServiceImpl(connection);

            Pizza pizza1 = new Pizza("Salami", 12.99);
            Pizza pizza2 = new Pizza("Prosciutto", 12.99);
            Pizza pizza3 = new Pizza("Margherita", 10.65);

            testService.createPizza(pizza1);
            testService.createPizza(pizza2);
            testService.createPizza(pizza3);

            Assertions.assertEquals(3, testService.list().size());
            Assertions.assertTrue(testService.list().contains(pizza1));

            testService.deletePizza(1);

            Assertions.assertEquals(2, testService.list().size());
            Assertions.assertFalse(testService.list().contains(pizza1));
        }

    }
}
