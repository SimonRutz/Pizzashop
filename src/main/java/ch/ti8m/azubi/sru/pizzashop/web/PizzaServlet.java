package ch.ti8m.azubi.sru.pizzashop.web;

import ch.ti8m.azubi.sru.pizzashop.dto.Pizza;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaService;
import ch.ti8m.azubi.sru.pizzashop.service.PizzaServiceRegistry;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Pizza")
public class PizzaServlet extends HttpServlet {

    private Template template;

    private PizzaService pizzaService() {
        return PizzaServiceRegistry.getInstance().get(PizzaService.class);
    }

    @Override
    public void init() throws ServletException {
        template = new FreemarkerConfig().loadTemplate("PizzaSelection.ftl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Pizza> pizzas = null;
        try {
            pizzas = pizzaService().list();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Writer writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("pizzas", pizzas);

        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }



}
