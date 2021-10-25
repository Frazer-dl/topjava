package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(SECOND_USER_MEAL_ID, SECOND_USER_ID);
        assertMatch(meal, TEST_MEAL);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(FIRST_USER_MEAL_ID, NOT_FOUND));
        assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND, FIRST_USER_ID));
    }

    @Test
    public void delete() {
        mealService.delete(FIRST_USER_MEAL_ID, FIRST_USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(FIRST_USER_MEAL_ID, FIRST_USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(FIRST_USER_MEAL_ID, SECOND_USER_ID));
        assertThrows(NotFoundException.class, () -> mealService.get(SECOND_USER_MEAL_ID, FIRST_USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealList = mealService.getBetweenInclusive(START_DATE, END_DATE, SECOND_USER_ID);
        assertMatch(mealList, mealsFiltered);
        mealList = mealService.getBetweenInclusive(null, null, SECOND_USER_ID);
        assertMatch(mealList, meals);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = mealService.getAll(SECOND_USER_ID);
        assertMatch(mealList, meals);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, FIRST_USER_ID);
        assertMatch(mealService.get(FIRST_USER_MEAL_ID, FIRST_USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal meal = mealService.create(getNew(), FIRST_USER_ID);
        Integer newId = meal.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(meal, newMeal);
        assertMatch(mealService.get(newId, FIRST_USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal meal = TEST_MEAL;
        meal.setDescription("TEST");
        assertThrows(DuplicateKeyException.class, () -> mealService.create(meal, SECOND_USER_ID));
    }
}