package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryMealRepository")
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> save(m, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} userId={}", meal, userId);
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (mealMap == null) return null;
        if (meal.isNew()) {
            Integer id = counter.incrementAndGet();
            meal.setId(id);
            return mealMap.computeIfAbsent(id, v -> meal);
        }
        return mealMap.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} userId={}", id, userId);
        if (repository.get(userId) != null) {
            return repository.get(userId).remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} userId={}", id, userId);
        if (repository.get(userId) != null) {
            return repository.get(userId).get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll {} ", userId);
        if (repository.get(userId) == null) {
            return Collections.emptyList();
        } else {
            return repository.get(userId).values();
        }
    }

    @Override
    public Collection<MealTo> getFiltered(int user, LocalDateTime start, LocalDateTime end, int caloriesPerDay) {
        log.info("getFiltered {} start={} end={} calories= {}", user, start, end, caloriesPerDay);
        Collection<Meal> meals = getAll(user);
        if (meals.isEmpty()) {
            return Collections.emptyList();
        } else {
            return MealsUtil.getFilteredTos(meals, caloriesPerDay, start.toLocalTime(), end.toLocalTime()).stream()
                    .sorted(Comparator.comparing((MealTo m) -> m.getDateTime().toLocalTime()).reversed())
                    .sorted(Comparator.comparing((MealTo m) -> m.getDateTime().toLocalDate()).reversed())
                    .collect(Collectors.toList());
        }
    }
}

