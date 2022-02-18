package ch.ti8m.azubi.sru.pizzashop.web;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.sru.pizzashop.persistence.DataBaseConnection;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    PizzaServiceImpl pizzaService = new PizzaServiceImpl(dataBaseConnection.connection());
    PizzaOrderServiceImpl pizzaOrderService = new PizzaOrderServiceImpl(dataBaseConnection.connection());

    Map<Integer, Integer> orderedPizzas = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Pizza> pizzaList = null;

        if (req.getParameter("searchBar") != null) {
            try {
                if (Objects.equals(req.getParameter("searchBar"), "")) {
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
            for (int i = 0; i < pizzaService.list().size()-1; i++ ) {
                Pizza pizza = pizzaService.list().get(i);
                if (req.getParameter(pizza.getName() + "Amount") != null) {
                    orderedPizzas.put(pizza.getID(), Integer.parseInt(req.getParameter(pizza.getName() + "Amount")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        doGet(req, resp);
    }

}
