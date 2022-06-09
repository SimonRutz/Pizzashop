package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaOrderDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class PizzaOrderServiceImpl implements PizzaOrderService{

    private Connection connection;
    private PizzaOrderDAO pizzaOrderDAO;

    public PizzaOrderServiceImpl() {
    }

    public PizzaOrderServiceImpl(Connection connection) {
        this.connection = connection;
        pizzaOrderDAO = new PizzaOrderDAO(connection);
    }

    @Override
    public PizzaOrder createPizzaOrder(PizzaOrder pizzaOrder) throws IllegalArgumentException, SQLException {

        if (pizzaOrder == null) {
            throw new IllegalArgumentException("PizzaOrder is required for creation");
        }

        pizzaOrderDAO.create(pizzaOrder);

        return pizzaOrder;
    }

    @Override
    public void combinePizzaOrder(PizzaOrder pizzaOrder) throws IllegalArgumentException, SQLException {

        PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(connection);

        PizzaOrder existingOrder = pizzaOrderDAO.getExistingOrder(pizzaOrder.getPizzaID());

        if(existingOrder == null) {
            throw new IllegalArgumentException();
        }

        pizzaOrderDAO.combine(pizzaOrder, existingOrder);
    }

    @Override
    public PizzaOrder getPizzaOrder(int pizzaID, int orderID) throws NoSuchElementException, SQLException {

        PizzaOrder pizzaOrder = pizzaOrderDAO.get(pizzaID, orderID);

        if (pizzaOrder == null) {
            throw new NoSuchElementException("PizzaOrder with this ID doesn't exist");
        }

        return pizzaOrder;
    }

    @Override
    public PizzaOrder getExistingOrder(int pizzaID) throws NoSuchElementException, SQLException {

        PizzaOrder pizzaOrder = pizzaOrderDAO.getExistingOrder(pizzaID);

        if (pizzaOrder == null) {
            throw new NoSuchElementException("PizzaOrder with this ID doesn't exist");
        }

        return pizzaOrder;
    }

    @Override
    public List<PizzaOrder> list() throws SQLException {

        return pizzaOrderDAO.list();
    }

    @Override
    public void update(PizzaOrder pizzaOrder) throws SQLException {

        pizzaOrderDAO.update(pizzaOrder);
    }

    @Override
    public void finishPizzaOrder(int order_id) throws IllegalArgumentException, SQLException {

        pizzaOrderDAO.finishOrder(order_id);
    }

    @Override
    public void deletePizzaOrder(int pizzaID, int orderID) throws SQLException {

        pizzaOrderDAO.delete(pizzaID, orderID);
    }

    @Override
    public void deleteExistingOrder(int pizzaID) throws SQLException {

        pizzaOrderDAO.deleteExistingOrder(pizzaID);
    }
}
