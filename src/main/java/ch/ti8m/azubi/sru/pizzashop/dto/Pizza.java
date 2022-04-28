package ch.ti8m.azubi.sru.pizzashop.dto;

import java.util.Objects;

public class Pizza {

    private Integer id;
    private String name;
    private Double price;
    private Integer amount;

    public Pizza() {}

    public Pizza(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Pizza(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Pizza(Integer id, String name, Double price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
         this.amount = amount;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (id == null) {
            return false;
        }
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(id, pizza.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }

}
