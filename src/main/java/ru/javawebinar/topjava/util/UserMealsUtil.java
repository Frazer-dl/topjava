package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


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

        System.out.println("filteredByCycles:");
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println("filteredByStreams:");
        filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 2000).forEach(System.out::println);
        System.out.println("optionalFilteredByCycles:");
        optionalFilteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 2000).forEach(System.out::println);
        System.out.println("optionalFilteredByStreams:");
        optionalFilteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 2000).forEach(System.out::println);

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();

        for (UserMeal meal : meals) {
            caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        for (UserMeal meal: meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesSumPerDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals,
                                                             LocalTime startTime,
                                                             LocalTime endTime,
                                                             int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = meals.stream()
                .collect(Collectors.toMap(p -> p.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(),
                        startTime,
                        endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesSumPerDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
    public static List<UserMealWithExcess> optionalFilteredByCycles(List<UserMeal> meals,
                                                                    LocalTime startTime,
                                                                    LocalTime endTime,
                                                                    int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();

        for (UserMeal userMeal : meals) {
            caloriesSumPerDate.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);

            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesSumPerDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay);
                userMealWithExcessList.add(userMealWithExcess);
            }
        }

        return userMealWithExcessList;
    }
    public static List<UserMealWithExcess> optionalFilteredByStreams(List<UserMeal> meals,
                                                                        LocalTime startTime,
                                                                        LocalTime endTime,
                                                                        int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(p -> p.getDateTime().toLocalDate()))
                .values()
                .stream()
                .flatMap(p -> {
                    int caloriesSumPerDate = p.stream()
                            .map(UserMeal::getCalories)
                            .reduce(0, Integer::sum);
                    return p.stream()
                            .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(),
                                    startTime,
                                    endTime))
                            .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),
                                    userMeal.getDescription(),
                                    userMeal.getCalories(),
                                    caloriesSumPerDate > caloriesPerDay));
                })
                .collect(Collectors.toList());
    }
}
