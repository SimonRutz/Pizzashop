package ch.ti8m.azubi.sru.pizzashop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private Integer id;
    @JsonIgnore
    private Timestamp orderDateTime;
    private String phoneNumber;
    private String address;

    public Order() {}

    public Order(Timestamp orderDateTime, String phoneNumber, String address) {
        this.orderDateTime = orderDateTime;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Order(Integer id, Timestamp orderDateTime, String phoneNumber, String address) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public Timestamp getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Timestamp orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDateTime=" + orderDateTime +
                ", phoneNumber=" + phoneNumber +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (id == null) {
            return false;
        }
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }


}
