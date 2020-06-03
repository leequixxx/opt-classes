package space.leequixxx.optclasses.data.repository;

import java.util.List;

public interface Repository<T> {
    T get(String id) throws Exception;

    List<T> all() throws Exception;

    T add(T entity) throws Exception;

    void remove(T entity) throws Exception;

    T update(T entity) throws Exception;
}
