package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealToDao;
import ru.javawebinar.topjava.DAO.MealToDaoImpl;
import ru.javawebinar.topjava.model.MealTo;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    MealToDao cache = new MealToDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        if (request.getParameter("id") == null && request.getParameter("edit") == null) {
            cache.init();
            List<MealTo> meals = cache.getAll();
            request.setAttribute("list", meals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        if (request.getParameter("edit").equals("1")) {
            Long id = Long.parseLong(request.getParameter("id"));
            MealTo meal = cache.getCacheById(id);
            request.setAttribute("id", meal.getId());
            request.setAttribute("date", meal.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            request.setAttribute("description", meal.getDescription());
            request.setAttribute("calories", meal.getCalories());
            request.setAttribute("isExcess", meal.isExcess());
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        }
        if (request.getParameter("edit").equals("2")) {
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean save = request.getParameter("save") != null;
        request.setAttribute("cache", cache);

        if (save && request.getParameter("id").equals("null")) {
            request.getRequestDispatcher("add.jsp").forward(request, response);
        }
        if (save && !request.getParameter("id").equals("null")) {
            request.getRequestDispatcher("update.jsp").forward(request, response);
        }
    }
}
