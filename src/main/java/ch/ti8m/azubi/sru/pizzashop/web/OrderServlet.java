package ch.ti8m.azubi.sru.pizzashop.web;

import ch.ti8m.azubi.sru.pizzashop.dto.Order;
import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;


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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Pizza> pizzaList = new LinkedList<>();
        List<PizzaOrder> pizzaOrderList = new LinkedList<>();
        double total = 0.0;

        Writer writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();

        try {
            pizzaOrderList = pizzaOrderService.list();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (PizzaOrder po : pizzaOrderList) {
            if (po.getOrderID() == 0) {

                total += po.getAmount() * po.getPizza().getPrice();

                Pizza pizza = po.getPizza();
                pizza.setAmount(po.getAmount());

                pizzaList.add(pizza);
            }
        }

        model.put("total", total);
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
            if (!phoneNumber.equals("") & !address.equals("")) {
                Order newOrder = new Order(dateTime, phoneNumber, address);
                orderService.createOrder(newOrder);
                pizzaOrderService.finishPizzaOrder(newOrder.getID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
