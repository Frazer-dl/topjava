package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealToDao;
import ru.javawebinar.topjava.DAO.MealToDaoImpl;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    MealToDao cache = new MealToDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        cache.init();
        List<MealTo> meals = MealsUtil.filteredByStreams(cache.getAll(), LocalTime.of(7, 0), LocalTime.of(21, 0), 2000);

        if (request.getParameter("id") == null && request.getParameter("edit") == null) {

            request.setAttribute("list", meals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        if (request.getParameter("edit").equals("1")) {
            Long id = Long.parseLong(request.getParameter("id"));
            MealTo mealTo = meals.stream().filter(m -> Objects.equals(m.getId(), id)).findFirst().get();
            request.setAttribute("id", mealTo.getId());
            request.setAttribute("date", mealTo.getDateTime());
            request.setAttribute("description", mealTo.getDescription());
            request.setAttribute("calories", mealTo.getCalories());
            request.setAttribute("isExcess", mealTo.isExcess());
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
