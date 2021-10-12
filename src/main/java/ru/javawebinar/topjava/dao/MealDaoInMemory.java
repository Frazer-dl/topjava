package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemory implements MealDao {
    private final Map<Long, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public MealDaoInMemory() {
        Arrays.asList(
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ).forEach(this::create);
    }

    @Override
    public Meal create(Meal m) {
        long id = counter.incrementAndGet();
        return repository.computeIfAbsent(id, v -> new Meal(id, m.getDateTime(), m.getDescription(), m.getCalories()));
    }

    @Override
    public List<Meal> read() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Meal read(long id) {
        return repository.get(id);
    }

    @Override
    public Meal update(Meal m) {
        return repository.computeIfPresent(m.getId(), (k, v) -> new Meal(m.getId(), m.getDateTime(), m.getDescription(), m.getCalories()));
    }

    @Override
    public void delete(long id) {
        repository.remove(id);
    }
}
