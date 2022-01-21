import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.persistence.OrderDAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

public class OrderDAO_Test {

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
            OrderDAO test = new OrderDAO(connection);

            Order testOrder = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            test.create(testOrder);

            Assertions.assertNotNull(testOrder.getID());
        }
    }

    @Test
    public void list() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderDAO test = new OrderDAO(connection);

            Order testOrder1 = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            Order testOrder2 = new Order(new Date(System.currentTimeMillis()), "0765432123", "SQLstrasse 134");
            Order testOrder3 = new Order(new Date(System.currentTimeMillis()), "0799999998", "Javastrasse 4");

            test.create(testOrder1);
            test.create(testOrder2);
            test.create(testOrder3);


            List<Order> testList = test.list();

            Assertions.assertNotNull(testList);

            Assertions.assertTrue(testList.contains(testOrder1));
            Assertions.assertTrue(testList.contains(testOrder2));
            Assertions.assertTrue(testList.contains(testOrder3));
        }
    }

    @Test
    public void get() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderDAO test = new OrderDAO(connection);

            Order testOrder1 = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            Order testOrder2 = new Order(new Date(System.currentTimeMillis()), "0765432123", "SQLstrasse 134");
            Order testOrder3 = new Order(new Date(System.currentTimeMillis()), "0799999998", "Javastrasse 4");

            test.create(testOrder1);
            test.create(testOrder2);
            test.create(testOrder3);

            Assertions.assertEquals(testOrder1, test.get(1));
            Assertions.assertEquals(testOrder2, test.get(2));
            Assertions.assertEquals(testOrder3, test.get(3));

        }
    }

    @Test
    public void update() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderDAO test = new OrderDAO(connection);

            Order testOrder1 = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            Order testOrder2 = new Order(new Date(System.currentTimeMillis()), "0765432123", "SQLstrasse 134");
            Order testOrder3 = new Order(new Date(System.currentTimeMillis()), "0799999998", "Javastrasse 4");

            test.create(testOrder1);
            test.create(testOrder2);
            test.create(testOrder3);

            Order updatedPizza = new Order(testOrder2.getID(), new Date(System.currentTimeMillis()), "0755555553", "UpdateErfolgreichStrasse 11");
            test.update(updatedPizza);

            Assertions.assertEquals(updatedPizza, test.get(testOrder2.getID()));
        }
    }

    @Test
    public void delete() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderDAO test = new OrderDAO(connection);

            Order testOrder1 = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            Order testOrder2 = new Order(new Date(System.currentTimeMillis()), "0765432123", "SQLstrasse 134");
            Order testOrder3 = new Order(new Date(System.currentTimeMillis()), "0799999998", "Javastrasse 4");

            test.create(testOrder1);
            test.create(testOrder2);
            test.create(testOrder3);

            Assertions.assertNotNull(test.get(testOrder2.getID()));
            Assertions.assertEquals(3, test.list().size());

            test.delete(testOrder2.getID());

            Assertions.assertNull(test.get(testOrder2.getID()));
            Assertions.assertEquals(2, test.list().size());
        }
    }

    @Test
    public void save() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderDAO test = new OrderDAO(connection);

            Order testOrder1 = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            Order testOrder2 = new Order(new Date(System.currentTimeMillis()), "0765432123", "SQLstrasse 134");
            Order testOrder3 = new Order(new Date(System.currentTimeMillis()), "0799999998", "Javastrasse 4");

            test.create(testOrder1);
            test.create(testOrder2);
            test.create(testOrder3);

            Order updatedOrder = new Order(1, new Date(System.currentTimeMillis()), "Salami", "UpdateStrasse 17");
            Order newOrder = new Order(new Date(System.currentTimeMillis()), "Marinara", "NeueStrasse 4");

            test.save(updatedOrder);
            test.save(newOrder);

            Assertions.assertEquals(1, updatedOrder.getID());
            Assertions.assertEquals(4, newOrder.getID());

        }
    }

    @Test
    public void find() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            OrderDAO test = new OrderDAO(connection);

            Order testOrder1 = new Order(new Date(System.currentTimeMillis()), "0774344865", "SQLstrasse 134");
            Order testOrder2 = new Order(new Date(System.currentTimeMillis()), "0765432123", "SQLstrasse 134");
            Order testOrder3 = new Order(new Date(System.currentTimeMillis()), "0799999998", "Javastrasse 4");

            test.create(testOrder1);
            test.create(testOrder2);
            test.create(testOrder3);

            String searchString1 = "0799999998";
            String searchString2 = "SQLstrasse 134";

            Assertions.assertEquals(testOrder3, test.find(searchString1).get(0));

            Assertions.assertTrue(test.find(searchString2).contains(testOrder1));
            Assertions.assertTrue(test.find(searchString2).contains(testOrder2));
        }
    }
}
