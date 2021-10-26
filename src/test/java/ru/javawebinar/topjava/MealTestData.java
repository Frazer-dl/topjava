package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int FIRST_USER_ID = START_SEQ;
    public static final int SECOND_USER_ID = START_SEQ + 1;
    public static final int FIRST_USER_MEAL_ID = START_SEQ + 5;
    public static final int SECOND_USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int SECOND_USER_MEAL_ID_2 = START_SEQ + 7;
    public static final int SECOND_USER_MEAL_ID_3 = START_SEQ + 10;
    public static final int SECOND_USER_MEAL_ID_4 = START_SEQ + 9;
    public static final int SECOND_USER_MEAL_ID_5 = START_SEQ + 3;
    public static final int SECOND_USER_MEAL_ID_6 = START_SEQ + 8;
    public static final int NOT_FOUND = 10;
    public static final Meal secondUserMeal_1 = new Meal(SECOND_USER_MEAL_ID_1, LocalDateTime.of(2021,7, 11, 6,51,10), "Breakfast", 2128);
    public static final Meal secondUserMeal_2 = new Meal(SECOND_USER_MEAL_ID_2, LocalDateTime.of(2021,4,27, 17,25,54),"Lunch", 1021);
    public static final Meal secondUserMeal_3 = new Meal(SECOND_USER_MEAL_ID_3, LocalDateTime.of(2021,3,24, 18,5,52),"Breakfast", 1885);
    public static final Meal secondUserMeal_4 = new Meal(SECOND_USER_MEAL_ID_4, LocalDateTime.of(2021,3,23, 13,29,11),"Lunch", 1488);
    public static final Meal secondUserMeal_5 = new Meal(SECOND_USER_MEAL_ID_5, LocalDateTime.of(2020, 12, 19, 10,7,4), "Breakfast", 416);
    public static final Meal secondUserMeal_6 = new Meal(SECOND_USER_MEAL_ID_6, LocalDateTime.of(2020,11,6, 20,52,52),"Afternoon snack", 1016);
    public static final Meal firstUserMeal = new Meal(FIRST_USER_MEAL_ID, LocalDateTime.of(2021,4,15,7,37,50).truncatedTo(ChronoUnit.SECONDS), "Afternoon snack", 1907);
    public static final List<Meal> meals = Arrays.asList(secondUserMeal_1, secondUserMeal_2, secondUserMeal_3, secondUserMeal_4, secondUserMeal_5, secondUserMeal_6);
    public static final LocalDate START_DATE = LocalDate.of(2021, 4, 27);
    public static final LocalDate END_DATE = LocalDate.of(2021, 7, 11);
    public static final List<Meal> mealsFiltered = Arrays.asList(secondUserMeal_1, secondUserMeal_2);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Description", 1555);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(secondUserMeal_1);
        meal.setDateTime(LocalDateTime.of(2021, 12, 12, 23,59,59).truncatedTo(ChronoUnit.MINUTES));
        meal.setDescription("new description");
        meal.setCalories(200);
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(LocalDateTime actual, LocalDateTime expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
