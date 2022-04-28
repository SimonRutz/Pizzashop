package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.persistence.OrderDAO;
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

        boolean containsPizza = false;
        int listID = 0;

        for (int i = 0; i < pizzaOrderDAO.list().size() - 1; i++) {
            if (pizzaOrderDAO.list().get(i).getPizza() == pizzaOrder.getPizza()) {// & pizzaOrderDAO.list().get(i).getOrderID() == 0) {
                containsPizza = true;
                listID = i;
            }
        }

        if (containsPizza) {

        } else {
            pizzaOrderDAO.create(pizzaOrder);
        }

        return pizzaOrder;
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
    public List<PizzaOrder> list() throws SQLException {

        return pizzaOrderDAO.list();
    }

    @Override
    public void updatePizzaOrder(int order_id) throws IllegalArgumentException, SQLException {

        pizzaOrderDAO.update(order_id);
    }

    @Override
    public void deletePizzaOrder(int pizzaID, int orderID) throws SQLException {

        pizzaOrderDAO.delete(pizzaID, orderID);
    }
}
