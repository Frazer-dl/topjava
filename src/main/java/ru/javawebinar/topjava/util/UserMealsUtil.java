package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;



public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 2000);
        mealsTo.forEach(System.out::println);

        filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal meal: meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        getCaloriesPerDay(meal.getDateTime().toLocalDate(), meals) > caloriesPerDay));
            }
        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(
                        ArrayList::new,
                        (list, item) -> list.add(new UserMealWithExcess(
                                item.getDateTime(),
                                item.getDescription(),
                                item.getCalories(),
                                getCaloriesPerDay(item.getDateTime().toLocalDate(), meals) > caloriesPerDay)),
                        ArrayList::addAll);
    }

    public static int getCaloriesPerDay(LocalDate localDate, List<UserMeal> meals) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : meals) {
            caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        return  caloriesSumPerDate.get(localDate);
    }
}
