package space.leequixxx.optclasses.data.repository.observer;

import java.sql.SQLException;

public interface EntityObserver<T> {
    void onAction(T entity) throws Exception;
}
