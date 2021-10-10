package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class MealDaoImpl implements MealDao {
    Map<Long, Meal> repository = new ConcurrentHashMap<>();
    AtomicLong size = new AtomicLong();

    public MealDaoImpl() {
        if (read().isEmpty()) {
            Arrays.asList(
                    new Meal(1L, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new Meal(2L, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new Meal(3L, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new Meal(4L, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new Meal(5L, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(6L, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new Meal(7L, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            ).forEach(this::create);
        }
    }

    @Override
    public void update(Meal m) {
        repository.put(m.getId(), m);
    }

    @Override
    public void create(Meal m) {
        repository.put(size.incrementAndGet(), m);
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
    public void delete(long id) {
        repository.remove(id);
        size.decrementAndGet();
    }
}
