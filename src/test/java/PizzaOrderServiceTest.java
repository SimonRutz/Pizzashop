import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaOrderDAO;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PizzaOrderServiceTest {

    @BeforeEach
    public void setup() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {

            try (Statement statement = connection.createStatement()) {
                statement.execute("drop table if exists pizza_ordering");
                statement.execute("drop table if exists pizza");
                statement.execute("drop table if exists orders");
            }

            try (Statement statement = connection.createStatement()) {

                statement.execute("create table pizza ( \n" +
                        "id int auto_increment, \n" +
                        "name varchar(30) not null, \n" +
                        "price double not null, \n" +
                        "primary key (id))"
                );
                statement.execute("insert into pizza (name, price) values ('Salami', 12.99)");
                statement.execute("insert into pizza (name, price) values ('Prosciutto', 12.99)");
                statement.execute("insert into pizza (name, price) values ('Margherita', 10.65)");

                statement.execute("create table orders ( \n" +
                        "id int auto_increment, \n" +
                        "orderDateTime datetime not null," +
                        "phoneNumber varchar(30) not null, \n" +
                        "address varchar(50) not null, \n" +
                        "primary key (id))"
                );
                statement.execute("insert into orders (orderDateTime, phoneNumber, address) values (current_date , '9999999999', 'TestStrasse 4')");
                statement.execute("insert into orders (orderDateTime, phoneNumber, address) values (current_date, '8888888888', 'TestStrasse 5')");
                statement.execute("insert into orders (orderDateTime, phoneNumber, address) values (current_date, '7777777777', 'TestStrasse 4')");

                statement.execute("create table pizza_ordering ( \n" +
                        "amount int not null, \n" +
                        "pizza_ID int not null, \n" +
                        "orders_ID int not null, \n" +
                        "foreign key(pizza_ID) references pizza(id)," +
                        "foreign key(orders_ID) references orders(id))"
                );
            }
        }
    }

    @Test
    public void create() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderServiceImpl testService = new PizzaOrderServiceImpl(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder pizzaOrder = new PizzaOrder(3, pizzaDAO.find("Prosciutto").get(0), 1);
            testService.createPizzaOrder(pizzaOrder);

            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(connection);
            Assertions.assertEquals(pizzaOrder, pizzaOrderDAO.get(2, 1));
        }
    }

    @Test
    public void get() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderServiceImpl testService = new PizzaOrderServiceImpl(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder pizzaOrder1 = new PizzaOrder(3, pizzaDAO.find("Prosciutto").get(0), 1);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 3);
            PizzaOrder pizzaOrder3 = new PizzaOrder(6, pizzaDAO.find("Salami").get(0), 1);

            testService.createPizzaOrder(pizzaOrder1);
            testService.createPizzaOrder(pizzaOrder2);
            testService.createPizzaOrder(pizzaOrder3);

            Assertions.assertEquals(pizzaOrder2, testService.getPizzaOrder(2, 3));
        }

    }

    @Test
    public void list() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderServiceImpl testService = new PizzaOrderServiceImpl(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder pizzaOrder1 = new PizzaOrder(3, pizzaDAO.find("Prosciutto").get(0), 1);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 3);
            PizzaOrder pizzaOrder3 = new PizzaOrder(6, pizzaDAO.find("Salami").get(0), 1);

            testService.createPizzaOrder(pizzaOrder1);
            testService.createPizzaOrder(pizzaOrder2);
            testService.createPizzaOrder(pizzaOrder3);

            List<PizzaOrder> expectedList = new LinkedList<>();
            expectedList.add(pizzaOrder1);
            expectedList.add(pizzaOrder2);
            expectedList.add(pizzaOrder3);

            Assertions.assertEquals(expectedList, testService.list());
        }

    }

    @Test
    public void update() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderServiceImpl testService = new PizzaOrderServiceImpl(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder pizzaOrder1 = new PizzaOrder(3, pizzaDAO.find("Prosciutto").get(0), 1);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 3);
            PizzaOrder pizzaOrder3 = new PizzaOrder(6, pizzaDAO.find("Salami").get(0), 1);

            testService.createPizzaOrder(pizzaOrder1);
            testService.createPizzaOrder(pizzaOrder2);
            testService.createPizzaOrder(pizzaOrder3);

            PizzaOrder updateOrder = new PizzaOrder(2, pizzaDAO.find("Salami").get(0), 1);

            Assertions.assertEquals(pizzaOrder3, testService.getPizzaOrder(1, 1));

            testService.update(updateOrder);

            Assertions.assertEquals(updateOrder, testService.getPizzaOrder(1, 1));
        }
    }

    @Test
    public void delete() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderServiceImpl testService = new PizzaOrderServiceImpl(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder pizzaOrder1 = new PizzaOrder(3, pizzaDAO.find("Prosciutto").get(0), 1);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 3);
            PizzaOrder pizzaOrder3 = new PizzaOrder(6, pizzaDAO.find("Salami").get(0), 1);

            testService.createPizzaOrder(pizzaOrder1);
            testService.createPizzaOrder(pizzaOrder2);
            testService.createPizzaOrder(pizzaOrder3);

            Assertions.assertEquals(3, testService.list().size());
            Assertions.assertTrue(testService.list().contains(pizzaOrder1));

            testService.deletePizzaOrder(2, 1);

            Assertions.assertEquals(2, testService.list().size());
            Assertions.assertFalse(testService.list().contains(pizzaOrder1));
        }

    }
}
