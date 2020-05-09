package space.leequixxx.optclasses.data.repository;

import java.io.IOException;
import java.util.List;

public interface Repository<T> {
    T get(String id) throws Exception;

    List<T> all() throws Exception;

    void add(T entity) throws Exception;

    void remove(T entity) throws Exception;

    void update(T entity) throws Exception;
}
