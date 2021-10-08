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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    MealToDao cache = new MealToDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        if (request.getParameter("id") == null) {
            if (cache.getAll().size() == 0) cache.init();
            List<MealTo> meals = cache.getAll();
            request.setAttribute("list", meals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else  {
            cache.deleteCache(Long.parseLong(request.getParameter("id")));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean save = request.getParameter("save") != null;
        String id = request.getParameter("id");
        System.out.println(id);
        if (save) {
            request.getRequestDispatcher("update.jsp").forward(request, response);
        }
    }
}
