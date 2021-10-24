package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int USER_ID = 100001;
    public static final int MEAL_ID = 100002;
    public static final int NOT_FOUND = 10;
    public static final Meal testMeal = new Meal(100001, LocalDateTime.of(2021, 7, 11, 6,51,10), "Breakfast", 2128);
    public static final List<Meal> meals = Arrays.asList(
            new Meal(100001, LocalDateTime.of(2021,7, 11, 6,51,10), "Breakfast", 2128),
            new Meal(100001, LocalDateTime.of(2021,4,27, 17,25,54),"Lunch", 1021),
            new Meal(100001, LocalDateTime.of(2021,3,24, 18,5,52),"Breakfast", 1885),
            new Meal(100001, LocalDateTime.of(2021,3,23, 13,29,11),"Lunch", 1488),
            new Meal(100001, LocalDateTime.of(2020, 12, 19, 10,7,4), "Breakfast", 416),
            new Meal(100001, LocalDateTime.of(2020,11,6, 20,52,52),"Afternoon snack", 1016)
    );
    public static final LocalDate START_DATE = LocalDate.of(2021, 4, 27);
    public static final LocalDate END_DATE = LocalDate.of(2021, 7, 11);
    public static final List<Meal> mealsFiltered = Arrays.asList(
            new Meal(100001, LocalDateTime.of(2021,7, 11, 6,51,10), "Breakfast", 2128),
            new Meal(100001, LocalDateTime.of(2021,4,27, 17,25,54),"Lunch", 1021)
    );

    public static Meal getNew() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Description", 1555);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(testMeal);
        meal.setId(MEAL_ID);
        meal.setDateTime(LocalDateTime.of(2021, 12, 12, 23,59,59).truncatedTo(ChronoUnit.MINUTES));
        meal.setDescription("new description");
        meal.setCalories(200);
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
