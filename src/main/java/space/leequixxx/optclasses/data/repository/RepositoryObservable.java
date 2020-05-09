package space.leequixxx.optclasses.data.repository;

import space.leequixxx.optclasses.data.repository.observer.AddEntityObserver;
import space.leequixxx.optclasses.data.repository.observer.RemoveEntityObserver;
import space.leequixxx.optclasses.data.repository.observer.UpdateEntityObserver;

public interface RepositoryObservable<T> {
    void addAddObserver(AddEntityObserver<T> observer);
    void addUpdateObserver(UpdateEntityObserver<T> observer);
    void addRemoveObserver(RemoveEntityObserver<T> observer);

    void removeAddObserver(AddEntityObserver<T> observer);
    void removeUpdateObserver(UpdateEntityObserver<T> observer);
    void removeRemoveObserver(RemoveEntityObserver<T> observer);
}
