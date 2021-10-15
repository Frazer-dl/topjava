package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.USER));
//            System.out.println(adminUserController.getAll());

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
//            mealRestController.update(new Meal(LocalDateTime.now().withSecond(0).withNano(0), "description", 100), 1);
//            mealRestController.get(1, 2);
//            mealRestController.create(new Meal(LocalDateTime.now().withSecond(0).withNano(0), "description", 50), 2);
//            mealRestController.create(new Meal(LocalDateTime.now().withSecond(0).withNano(0), "description", 50), 2);
//            mealRestController.update(new Meal(LocalDateTime.now().withSecond(0).withNano(0), "description", 511), 2);
//            mealRestController.get(1, 2);
//            System.out.println(mealRestController.getAll(2));
//            mealRestController.delete(1,2);
//            mealRestController.getAll(2);
//            mealRestController.get(20, 1);
        }
    }
}
