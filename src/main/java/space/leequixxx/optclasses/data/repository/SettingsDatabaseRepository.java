package space.leequixxx.optclasses.data.repository;

import org.apache.commons.collections4.IterableUtils;
import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.data.repository.observer.AddEntityObserver;
import space.leequixxx.optclasses.data.repository.observer.EntityObserver;
import space.leequixxx.optclasses.data.repository.observer.RemoveEntityObserver;
import space.leequixxx.optclasses.data.repository.observer.UpdateEntityObserver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SettingsDatabaseRepository implements Repository<Database>, RepositoryObservable<Database> {
    private Settings settings;

    private List<AddEntityObserver<Database>> addEntityObservers = new LinkedList<>();
    private List<UpdateEntityObserver<Database>> updateEntityObservers = new LinkedList<>();
    private List<RemoveEntityObserver<Database>> removeEntityObservers = new LinkedList<>();

    public SettingsDatabaseRepository(Settings settings) {
        this.settings = settings;
    }

    @Override
    public Database get(String id) {
        return IterableUtils.find(settings.getDatabases(), database -> database.getPath().equals(id));
    }

    @Override
    public List<Database> all() {
        return settings.getDatabases();
    }

    @Override
    public void add(Database entity) throws Exception {
        settings.getDatabases().add(entity);
        Settings.save();

        for (EntityObserver<Database> observer :
                addEntityObservers) {
            observer.onAction(entity);
        }
    }

    @Override
    public void remove(Database entity) throws Exception {
        settings.getDatabases().remove(entity);
        Settings.save();

        for (EntityObserver<Database> observer :
                removeEntityObservers) {
            observer.onAction(entity);
        }
    }

    @Override
    public void update(Database entity) throws Exception {
        Settings.save();

        for (EntityObserver<Database> observer :
                updateEntityObservers) {
            observer.onAction(entity);
        }
    }

    @Override
    public void addAddObserver(AddEntityObserver<Database> observer) {
        addEntityObservers.add(observer);
    }

    @Override
    public void addUpdateObserver(UpdateEntityObserver<Database> observer) {
        updateEntityObservers.add(observer);
    }

    @Override
    public void addRemoveObserver(RemoveEntityObserver<Database> observer) {
        removeEntityObservers.add(observer);
    }

    @Override
    public void removeAddObserver(AddEntityObserver<Database> observer) {
        addEntityObservers.remove(observer);
    }

    @Override
    public void removeUpdateObserver(UpdateEntityObserver<Database> observer) {
        updateEntityObservers.remove(observer);
    }

    @Override
    public void removeRemoveObserver(RemoveEntityObserver<Database> observer) {
        removeEntityObservers.remove(observer);
    }
}
