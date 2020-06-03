package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.data.model.Database;

import java.sql.Connection;

public interface DatabaseSelectPresenter extends Presenter {
    void exit();
    void settings();
    void createDatabase();
    void editDatabase(Database database);
    void deleteDatabase(Database database);
    void openDatabase(Database database);
    void closeDatabase(Database database, Connection connection);
}
