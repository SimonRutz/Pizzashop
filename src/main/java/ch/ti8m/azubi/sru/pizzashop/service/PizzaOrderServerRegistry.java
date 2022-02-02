package ch.ti8m.azubi.sru.pizzashop.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PizzaOrderServerRegistry {

    private static PizzaOrderServerRegistry instance;

    private final Map<Class<?>, Object> services = new HashMap<>();

    private PizzaOrderServerRegistry() {
        services.put(PizzaOrderService.class, new PizzaOrderServiceImpl());
    }

    public static synchronized PizzaOrderServerRegistry getInstance() {
        if (instance == null) {
            instance = new PizzaOrderServerRegistry();
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
