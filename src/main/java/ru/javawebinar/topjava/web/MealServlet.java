package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController restController;

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        restController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : getId(request),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        SecurityUtil.setAuthUserId(getUserId(request));
        if (meal.isNew()) {
            restController.create(meal);
        } else {
            restController.update(meal, meal.getId());
        }
        response.sendRedirect(request.getContextPath()+ "/meals?userId=" + getUserId(request));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        SecurityUtil.setAuthUserId(getUserId(request));
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {} userId={}", id, getUserId(request));
                restController.delete(id);
                response.sendRedirect(request.getContextPath()+ "/meals?userId=" + getUserId(request));
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        restController.get(getId(request));
                request.setAttribute("userId", getUserId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                request.setAttribute("userId", getUserId(request));

                LocalDate startDate = request.getParameter("startDate").isEmpty() ?
                        LocalDate.MIN : LocalDate.parse(request.getParameter("startDate"));
                LocalTime startTime = request.getParameter("startTime").isEmpty() ?
                        LocalTime.MIN : LocalTime.parse(request.getParameter("startTime"));
                LocalDate endDate = request.getParameter("endDate").isEmpty() ?
                        LocalDate.MAX : LocalDate.parse(request.getParameter("endDate"));
                LocalTime endTime = request.getParameter("endTime").isEmpty() ?
                        LocalTime.MAX : LocalTime.parse(request.getParameter("endTime"));

                request.setAttribute("meals", restController.getFiltered(startDate, startTime, endDate, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            case "all":
            default:
                log.info("getAll {}", getUserId(request));
                request.setAttribute("userId", getUserId(request));
                request.setAttribute("meals", restController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private int getUserId(HttpServletRequest request) {
        String paramUserId = Objects.requireNonNull(request.getParameter("userId"));
        return Integer.parseInt(paramUserId);
    }
}
