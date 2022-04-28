import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaOrderDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PizzaOrderDAO_Test {

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
                statement.execute("insert into pizza (name, price) values ('Margherita', 10.50)");

                statement.execute("create table orders ( \n" +
                        "id int auto_increment, \n" +
                        "orderDateTime datetime not null," +
                        "phoneNumber varchar(30) not null, \n" +
                        "address varchar(50) not null, \n" +
                        "primary key (id))"
                );
                statement.execute("insert into orders (orderDateTime, phoneNumber, address) values (current_date , '0774344865', 'SQLstrasse 134')");
                statement.execute("insert into orders (orderDateTime, phoneNumber, address) values (current_date, '0765432123', 'SQLstrasse 134')");
                statement.execute("insert into orders (orderDateTime, phoneNumber, address) values (current_date, '0799999998', 'Javastrasse 4')");

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
            PizzaOrderDAO test = new PizzaOrderDAO(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder testPizzaOrder = new PizzaOrder(2, pizzaDAO.find("Salami").get(0) , 1);
            test.create(testPizzaOrder);

            Assertions.assertEquals(testPizzaOrder.getPizzaID(), 1);
            Assertions.assertEquals(testPizzaOrder.getPizza(), pizzaDAO.find("Salami").get(0));
        }
    }

    @Test
    public void list() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderDAO test = new PizzaOrderDAO(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder testPizzaOrder1 = new PizzaOrder(2, pizzaDAO.find("Salami").get(0), 1);
            PizzaOrder testPizzaOrder2 = new PizzaOrder(5, pizzaDAO.find("Margherita").get(0), 3);
            PizzaOrder testPizzaOrder3 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 1);


            test.create(testPizzaOrder1);
            test.create(testPizzaOrder2);
            test.create(testPizzaOrder3);

            List<PizzaOrder> testList = test.list();

            Assertions.assertNotNull(testList);

            Assertions.assertTrue(testList.contains(testPizzaOrder1));
            Assertions.assertTrue(testList.contains(testPizzaOrder2));
            Assertions.assertTrue(testList.contains(testPizzaOrder3));
        }
    }

    @Test
    public void get() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderDAO test = new PizzaOrderDAO(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder testPizzaOrder1 = new PizzaOrder(2, pizzaDAO.find("Salami").get(0), 1);
            PizzaOrder testPizzaOrder2 = new PizzaOrder(5, pizzaDAO.find("Margherita").get(0), 3);
            PizzaOrder testPizzaOrder3 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 1);


            test.create(testPizzaOrder1);
            test.create(testPizzaOrder2);
            test.create(testPizzaOrder3);

            Assertions.assertEquals(testPizzaOrder1, test.get(1,1));
            Assertions.assertEquals(testPizzaOrder3, test.get(2, 1));

            Assertions.assertEquals(testPizzaOrder2, test.get(3, 3));
        }
    }

    @Test
    public void update() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderDAO test = new PizzaOrderDAO(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder testPizzaOrder1 = new PizzaOrder(2, pizzaDAO.find("Salami").get(0), 1);
            PizzaOrder testPizzaOrder2 = new PizzaOrder(5, pizzaDAO.find("Margherita").get(0), 3);
            PizzaOrder testPizzaOrder3 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 1);


            test.create(testPizzaOrder1);
            test.create(testPizzaOrder2);
            test.create(testPizzaOrder3);

            PizzaOrder updatedPizzaOrder = new PizzaOrder(4, pizzaDAO.find("Salami").get(0), 1);
            test.update(2);

            Assertions.assertEquals(updatedPizzaOrder, test.get(1, 1));
        }
    }

    @Test
    public void delete() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()){
            PizzaOrderDAO test = new PizzaOrderDAO(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder testPizzaOrder1 = new PizzaOrder(2, pizzaDAO.find("Salami").get(0), 1);
            PizzaOrder testPizzaOrder2 = new PizzaOrder(5, pizzaDAO.find("Margherita").get(0), 3);
            PizzaOrder testPizzaOrder3 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 1);


            test.create(testPizzaOrder1);
            test.create(testPizzaOrder2);
            test.create(testPizzaOrder3);

            Assertions.assertEquals(testPizzaOrder2, test.get(3, 3));
        }
    }

    @Test
    public void find() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaOrderDAO test = new PizzaOrderDAO(connection);

            PizzaDAO pizzaDAO = new PizzaDAO(connection);
            PizzaOrder testPizzaOrder1 = new PizzaOrder(2, pizzaDAO.find("Salami").get(0), 1);
            PizzaOrder testPizzaOrder2 = new PizzaOrder(5, pizzaDAO.find("Margherita").get(0), 3);
            PizzaOrder testPizzaOrder3 = new PizzaOrder(1, pizzaDAO.find("Prosciutto").get(0), 1);


            test.create(testPizzaOrder1);
            test.create(testPizzaOrder2);
            test.create(testPizzaOrder3);

            int search1 = 1;
            int search2 = 3;

            Assertions.assertTrue(test.find(search1).contains(testPizzaOrder1));
            Assertions.assertTrue(test.find(search1).contains(testPizzaOrder3));

            Assertions.assertEquals(testPizzaOrder2, test.find(search2).get(0));


        }
    }
}
