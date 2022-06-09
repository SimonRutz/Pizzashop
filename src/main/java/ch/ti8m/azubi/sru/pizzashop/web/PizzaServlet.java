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

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/Pizza")
public class PizzaServlet extends HttpServlet {
    public PizzaServlet() throws SQLException, ClassNotFoundException {
    }

    private Template template;

    @Override
    public void init() throws ServletException {
        template = new FreemarkerConfig().loadTemplate("PizzaSelection.ftl");
    }

    DataBaseConnection dataBaseConnection = new DataBaseConnection();

    OrderServiceImpl orderService = new OrderServiceImpl(dataBaseConnection.connection());
    PizzaServiceImpl pizzaService = new PizzaServiceImpl(dataBaseConnection.connection());
    PizzaOrderServiceImpl pizzaOrderService = new PizzaOrderServiceImpl(dataBaseConnection.connection());

    Map<Integer, Integer> orderedPizzas = new TreeMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Pizza> pizzaList = null;

        if (req.getParameter("searchBar") != null) {
            try {
                if (req.getParameter("searchBar").equals("")) {
                    pizzaList = pizzaService.list();
                } else {
                    pizzaList = pizzaService.findPizza(req.getParameter("searchBar"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                pizzaList = pizzaService.list();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("pizzaList", pizzaList);

        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            throw new IOException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            for (Pizza p : pizzaService.list()) {
                Pizza pizza = pizzaService.list().get(p.getID() - 1);
                if (req.getParameter(pizza.getName() + "Amount") != null) {
                    if(req.getParameter(pizza.getName() + "Amount").equals("")) {
                        break;
                    } else if(pizzaOrderService.list().contains(pizzaOrderService.getExistingOrder(pizza.getID()))){
                        PizzaOrder pizzaOrder = new PizzaOrder(Integer.parseInt(req.getParameter(pizza.getName() + "Amount")), pizza.getID());
                        pizzaOrderService.combinePizzaOrder(pizzaOrder);
                    } else {
                        pizzaOrderService.createPizzaOrder(new PizzaOrder(Integer.parseInt(req.getParameter(pizza.getName() + "Amount")), pizza.getID()));
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        doGet(req, resp);
    }
}
