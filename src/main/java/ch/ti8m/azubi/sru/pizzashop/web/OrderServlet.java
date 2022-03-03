package ch.ti8m.azubi.sru.pizzashop.web;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;
import ch.ti8m.azubi.sru.pizzashop.service.OrderServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaOrderServiceImpl;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaServiceImpl;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@WebServlet("/Order")
public class OrderServlet extends HttpServlet {
    public OrderServlet() throws SQLException, ClassNotFoundException {
    }

    private Template template;

    @Override
    public void init() throws ServletException {
        template = new FreemarkerConfig().loadTemplate("Order.ftl");
    }

    DataBaseConnection dataBaseConnection = new DataBaseConnection();

    PizzaServiceImpl pizzaService = new PizzaServiceImpl(dataBaseConnection.connection());
    OrderServiceImpl orderService = new OrderServiceImpl(dataBaseConnection.connection());
    PizzaOrderServiceImpl pizzaOrderService = new PizzaOrderServiceImpl(dataBaseConnection.connection());
    PizzaServlet pizzaServlet = new PizzaServlet();

    Map<Integer, Integer> orderedPizzas = pizzaServlet.orderedPizzas;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Pizza> pizzaList = new LinkedList<>();

        for (Integer i : pizzaServlet.orderedPizzas.keySet()) {
            try {
                pizzaList.add(pizzaService.getPizza((int) orderedPizzas.keySet().toArray()[i]));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Writer writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("pizzaList", pizzaList);

        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String address = req.getParameter("addressForm");
        String phoneNumber = req.getParameter("phoneNumberForm");
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());

        try {
            orderService.createOrder(new Order(dateTime, phoneNumber, address));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
