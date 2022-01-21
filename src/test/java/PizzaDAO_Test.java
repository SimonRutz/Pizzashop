import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PizzaDAO_Test {

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
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza = new Pizza("Salami", 12.99);
            test.create(testPizza);

            Assertions.assertNotNull(testPizza.getID());
        }
    }

    @Test
    public void list() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza1 = new Pizza("Salami", 12.99);
            Pizza testPizza2 = new Pizza("Prosciutto", 12.99);
            Pizza testPizza3 = new Pizza("Margherita", 10.50);

            test.create(testPizza1);
            test.create(testPizza2);
            test.create(testPizza3);

            List<Pizza> testList = test.list();

            Assertions.assertNotNull(testList);

            Assertions.assertTrue(testList.contains(testPizza1));
            Assertions.assertTrue(testList.contains(testPizza2));
            Assertions.assertTrue(testList.contains(testPizza3));
        }
    }

    @Test
    public void get() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza1 = new Pizza("Salami", 12.99);
            Pizza testPizza2 = new Pizza("Prosciutto", 12.99);
            Pizza testPizza3 = new Pizza("Margherita", 10.50);

            test.create(testPizza1);
            test.create(testPizza2);
            test.create(testPizza3);

            Assertions.assertEquals(testPizza1, test.get(1));
            Assertions.assertEquals(testPizza2, test.get(2));
            Assertions.assertEquals(testPizza3, test.get(3));

        }
    }

    @Test
    public void update() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza1 = new Pizza("Salami", 12.99);
            Pizza testPizza2 = new Pizza("Prosciutto", 12.99);
            Pizza testPizza3 = new Pizza("Margherita", 10.50);

            test.create(testPizza1);
            test.create(testPizza2);
            test.create(testPizza3);

            Pizza updatedPizza = new Pizza(testPizza2.getID(), "Test", 99.99);
            test.update(updatedPizza);

            Assertions.assertEquals(updatedPizza, test.get(testPizza2.getID()));
        }
    }

    @Test
    public void delete() throws SQLException {

        try (Connection connection = ConnectionFactory.testConnection()){
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza1 = new Pizza("Salami", 12.99);
            Pizza testPizza2 = new Pizza("Prosciutto", 12.99);
            Pizza testPizza3 = new Pizza("Margherita", 10.50);

            test.create(testPizza1);
            test.create(testPizza2);
            test.create(testPizza3);

            Assertions.assertNotNull(test.get(testPizza2.getID()));
            Assertions.assertEquals(3, test.list().size());

            test.delete(testPizza2.getID());

            Assertions.assertNull(test.get(testPizza2.getID()));
            Assertions.assertEquals(2, test.list().size());
        }
    }

    @Test
    public void save() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza1 = new Pizza("Salami", 12.99);
            Pizza testPizza2 = new Pizza("Prosciutto", 12.99);
            Pizza testPizza3 = new Pizza("Margherita", 10.50);

            test.create(testPizza1);
            test.create(testPizza2);
            test.create(testPizza3);

            Pizza salamiUpdate = new Pizza(1, "Salami", 13.65);
            Pizza newPizza = new Pizza("Diavola", 14.99);

            test.save(salamiUpdate);
            test.save(newPizza);

            Assertions.assertEquals(4, newPizza.getID());
            Assertions.assertEquals(1, salamiUpdate.getID());

        }
    }

    @Test
    public void find() throws SQLException {
        try (Connection connection = ConnectionFactory.testConnection()) {
            PizzaDAO test = new PizzaDAO(connection);

            Pizza testPizza1 = new Pizza("Salami", 12.99);
            Pizza testPizza2 = new Pizza("Prosciutto", 12.99);
            Pizza testPizza3 = new Pizza("Margherita", 10.50);

            test.create(testPizza1);
            test.create(testPizza2);
            test.create(testPizza3);

            String searchString1 = "Margherita";
            String searchString2 = "12.99";

            Assertions.assertEquals(testPizza3, test.find(searchString1).get(0));

            Assertions.assertTrue(test.find(searchString2).contains(testPizza1));
            Assertions.assertTrue(test.find(searchString2).contains(testPizza2));
        }
    }
}
