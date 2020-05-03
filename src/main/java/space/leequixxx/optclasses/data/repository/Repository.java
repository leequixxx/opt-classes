package space.leequixxx.optclasses.data.repository;

import java.util.List;

public interface Repository<T> {
    T get(long id);

    List<T> all();

    void add(T entity);

    void remove(T entity);

    void update(T entity);
}
