package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.data.model.Database;

public interface DatabaseSelectPresenter extends Presenter {
    void createDatabase();
    void editDatabase(Database database);
    void deleteDatabase(Database database);
}
