import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.persistence.OrderDAO;
import ch.ti8m.azubi.sru.pizzashop.service.OrderService;
import ch.ti8m.azubi.sru.pizzashop.service.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class OrderServiceTest {

    @BeforeEach
    public void setup() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {

            try (Statement statement = connection.createStatement()) {
                statement.execute("drop table if exists orders");
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("create table orders ( \n" +
                        "id int auto_increment, \n" +
                        "orderDateTime datetime not null," +
                        "phoneNumber varchar(30) not null, \n" +
                        "address varchar(50) not null, \n" +
                        "primary key (id))"
                );
            }
        }
    }

    @Test
    public void create() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderServiceImpl testService = new OrderServiceImpl(connection);

            Order order = new Order(new Date(System.currentTimeMillis()), "9999999999", "TestStrasse 4");
            testService.createOrder(order);

            OrderDAO orderDAO = new OrderDAO(connection);
            Assertions.assertEquals(order, orderDAO.get(1));
        }
    }

    @Test
    public void get() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderServiceImpl testService = new OrderServiceImpl(connection);

            Order order1 = new Order(new Date(System.currentTimeMillis()), "9999999999", "TestStrasse 4");
            Order order2 = new Order(new Date(System.currentTimeMillis()), "8888888888", "TestStrasse 5");
            Order order3 = new Order(new Date(System.currentTimeMillis()), "7777777777", "TestStrasse 4");

            testService.createOrder(order1);
            testService.createOrder(order2);
            testService.createOrder(order3);

            Assertions.assertEquals(order2, testService.getOrder(2));
        }

    }

    @Test
    public void list() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderServiceImpl testService = new OrderServiceImpl(connection);

            Order order1 = new Order(new Date(System.currentTimeMillis()), "9999999999", "TestStrasse 4");
            Order order2 = new Order(new Date(System.currentTimeMillis()), "8888888888", "TestStrasse 5");
            Order order3 = new Order(new Date(System.currentTimeMillis()), "7777777777", "TestStrasse 4");

            testService.createOrder(order1);
            testService.createOrder(order2);
            testService.createOrder(order3);

            List<Order> expectedList = new LinkedList<>();
            expectedList.add(order1);
            expectedList.add(order2);
            expectedList.add(order3);

            Assertions.assertEquals(expectedList, testService.list());
        }

    }

    @Test
    public void update() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderServiceImpl testService = new OrderServiceImpl(connection);

            Order order1 = new Order(new Date(System.currentTimeMillis()), "9999999999", "TestStrasse 4");
            Order order2 = new Order(new Date(System.currentTimeMillis()), "8888888888", "TestStrasse 5");
            Order order3 = new Order(new Date(System.currentTimeMillis()), "7777777777", "TestStrasse 4");

            testService.createOrder(order1);
            testService.createOrder(order2);
            testService.createOrder(order3);

            Order updateOrder = new Order(3, new Date(System.currentTimeMillis()), "7777777777", "UpdateStrasse 14");

            Assertions.assertEquals(order3, testService.getOrder(3));

            testService.updateOrder(updateOrder);

            Assertions.assertEquals(updateOrder, testService.getOrder(3));
        }
    }

    @Test
    public void delete() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderServiceImpl testService = new OrderServiceImpl(connection);

            Order order1 = new Order(new Date(System.currentTimeMillis()), "9999999999", "TestStrasse 4");
            Order order2 = new Order(new Date(System.currentTimeMillis()), "8888888888", "TestStrasse 5");
            Order order3 = new Order(new Date(System.currentTimeMillis()), "7777777777", "TestStrasse 4");

            testService.createOrder(order1);
            testService.createOrder(order2);
            testService.createOrder(order3);

            Assertions.assertEquals(3, testService.list().size());
            Assertions.assertTrue(testService.list().contains(order1));

            testService.deleteOrder(1);

            Assertions.assertEquals(2, testService.list().size());
            Assertions.assertFalse(testService.list().contains(order1));
        }

    }
}
