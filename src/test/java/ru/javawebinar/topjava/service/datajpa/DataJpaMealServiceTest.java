package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    @Transactional
    public void getWithUser() {
        User actualUser = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID).getUser();
        List<Meal> actualMeals = actualUser.getMeals();
        List<Meal> expectedMeals = Arrays.asList(adminMeal2, adminMeal1);
        MEAL_MATCHER.assertMatch(actualMeals, expectedMeals);
        USER_MATCHER.assertMatch(actualUser, admin);
    }

    @Test
    public void getWithUserNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void getWithUserNotOwn() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(MEAL1_ID, ADMIN_ID));
    }
}
