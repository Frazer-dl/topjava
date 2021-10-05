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

        List<UserMeal> meals2 = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 12, 0, 0), "aaa", 100),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 13, 14, 0), "sss", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 14, 13, 0), "ddd", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 15, 14, 0), "ff", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 16, 13, 0), "gg", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 17, 20, 0), "hh", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 18, 20, 0), "jj", 410)
        );
        System.out.println("optionalFilteredByCycles:");
        optionalFilteredByCycles(meals2, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000).forEach(System.out::println);
        System.out.println("filteredByCycles:");
        filteredByCycles(meals2, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();

        for (UserMeal meal : meals) {
            caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();

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
                .collect(Collectors.toMap(um -> um.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .filter(um -> TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(),
                        startTime,
                        endTime))
                .map(um -> new UserMealWithExcess(um.getDateTime(),
                        um.getDescription(),
                        um.getCalories(),
                        caloriesSumPerDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> optionalFilteredByCycles(List<UserMeal> meals,
                                                                    LocalTime startTime,
                                                                    LocalTime endTime,
                                                                    int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();

        class RecursiveCycles {
            int count = 0;
            public List<UserMealWithExcess> recursiveCycles() {
                while (count < meals.size()) {
                    int i = count;
                    caloriesSumPerDate.merge(meals.get(i).getDateTime().toLocalDate(), meals.get(i).getCalories(), Integer::sum);
                    if (TimeUtil.isBetweenHalfOpen(meals.get(i).getDateTime().toLocalTime(), startTime, endTime)) {
                        count++;
                        if (count != meals.size()) recursiveCycles();
                        userMealWithExcessList.add((new UserMealWithExcess(
                                meals.get(i).getDateTime(),
                                meals.get(i).getDescription(),
                                meals.get(i).getCalories(),
                                caloriesSumPerDate.get(meals.get(i).getDateTime().toLocalDate()) > caloriesPerDay)));
                    }
                    count++;
                }
                return userMealWithExcessList;
            }
        }

        return new RecursiveCycles().recursiveCycles();
    }

    public static List<UserMealWithExcess> optionalFilteredByStreams(List<UserMeal> meals,
                                                                        LocalTime startTime,
                                                                        LocalTime endTime,
                                                                        int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate()))
                .values()
                .stream()
                .flatMap(um -> {
                    int caloriesSumPerDate = um.stream()
                            .map(UserMeal::getCalories)
                            .reduce(0, Integer::sum);
                    return um.stream()
                            .filter(ums -> TimeUtil.isBetweenHalfOpen(ums.getDateTime().toLocalTime(),
                                    startTime,
                                    endTime))
                            .map(ums -> new UserMealWithExcess(ums.getDateTime(),
                                    ums.getDescription(),
                                    ums.getCalories(),
                                    caloriesSumPerDate > caloriesPerDay));
                })
                .collect(Collectors.toList());
    }
}
