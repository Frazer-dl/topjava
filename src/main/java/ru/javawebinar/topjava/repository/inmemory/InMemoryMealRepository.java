package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository()
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> save(m, 1));
        MealsUtil.meals2.forEach(m -> save(m, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} userId={}", meal, userId);
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            int id = counter.incrementAndGet();
            meal.setId(id);
            return mealMap.put(id, meal);
        }
        return mealMap.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} userId={}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap != null) {
            return mealMap.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} userId={}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap != null) {
            return mealMap.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {} ", userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap == null) {
            return Collections.emptyList();
        } else {
            return mealMap.values().stream()
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getFiltered {} startDate={} startTime={} endDate={} endTime={}", userId, startDate, startTime, endDate, endTime);
        Collection<Meal> meals = getAll(userId);
        if (meals.isEmpty()) {
            return Collections.emptyList();
        } else {
            return meals.stream()
                    .filter(m -> DateTimeUtil.isBetweenOpen(m.getDate(), startDate, endDate)
                            && DateTimeUtil.isBetweenHalfOpen(m.getTime(), startTime, endTime))
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
    }
}

