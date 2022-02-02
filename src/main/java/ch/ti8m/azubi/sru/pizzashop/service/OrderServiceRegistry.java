package ch.ti8m.azubi.sru.pizzashop.service;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class OrderServiceRegistry {

    private static OrderServiceRegistry instance;

    private final Map<Class<?>, Object> services = new HashMap<>();

    private OrderServiceRegistry() {
        services.put(OrderService.class, new OrderServiceRegistry());
    }

    public static synchronized OrderServiceRegistry getInstance() {
        if (instance == null) {
            instance = new OrderServiceRegistry();
        }
        return instance;
    }

    public <S> S get(Class<S> serviceClass) {
        S service = serviceClass.cast(services.get(serviceClass));
        if (service == null) {
            throw new NoSuchElementException("Service not found: " + serviceClass.getName());
        }
        return service;
    }
}
