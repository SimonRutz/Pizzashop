package ch.ti8m.azubi.sru.pizzashop.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PizzaOrderServiceRegistry {

    private static PizzaOrderServiceRegistry instance;

    private final Map<Class<?>, Object> services = new HashMap<>();

    private PizzaOrderServiceRegistry() {
        services.put(PizzaService.class, new PizzaServiceImpl());
    }

    public static synchronized PizzaOrderServiceRegistry getInstance() {
        if (instance == null) {
            instance = new PizzaOrderServiceRegistry();
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
