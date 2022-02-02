package ch.ti8m.azubi.sru.pizzashop.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PizzaServiceRegistry {

    private static PizzaServiceRegistry instance;

    private final Map<Class<?>, Object> services = new HashMap<>();

    private PizzaServiceRegistry() {
        services.put(PizzaService.class, new PizzaServiceImpl());
    }

    public static synchronized PizzaServiceRegistry getInstance() {
        if (instance == null) {
            instance = new PizzaServiceRegistry();
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

