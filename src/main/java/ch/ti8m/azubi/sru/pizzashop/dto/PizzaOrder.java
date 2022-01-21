package ch.ti8m.azubi.sru.pizzashop.dto;

import ch.ti8m.azubi.sru.pizzashop.persistence.PizzaDAO;

import javax.xml.soap.SOAPConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class PizzaOrder {

    private Integer amount;
    private Pizza pizza;
    private Integer pizzaID;
    private Integer orderID;

    public PizzaOrder() {}

    public PizzaOrder(Integer amount, Integer pizzaID, Integer orderID) {
        this.amount = amount;
        this.pizzaID = pizzaID;
        this.orderID = orderID;
    }

    public PizzaOrder(Integer amount, Integer pizzaID, Integer orderID, Connection connection) throws SQLException {
        this.amount = amount;
        this.pizzaID = pizzaID;
        this.orderID = orderID;

        PizzaDAO pizzaDAO = new PizzaDAO(connection);
        this.pizza = pizzaDAO.get(pizzaID);
    }

    public PizzaOrder(Integer amount, Pizza pizza, Integer orderID) {
        this.amount = amount;
        this.pizza = pizza;
        this.orderID = orderID;
        this.pizzaID = pizza.getID();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(Integer pizzaID) {
        this.pizzaID = pizzaID;
    }

    @Override
    public String toString() {
        return "PizzaOrder{" +
                "amount=" + amount +
                ", pizza=" + pizza +
                ", order=" + orderID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaOrder that = (PizzaOrder) o;
        return Objects.equals(amount, that.amount) && Objects.equals(pizza, that.pizza) && Objects.equals(orderID, that.orderID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, pizza, orderID);
    }
}
