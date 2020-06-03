package space.leequixxx.optclasses.ui.view;

import space.leequixxx.optclasses.data.model.Database;

public interface DatabaseSelectView extends View {
    void onDatabaseAdd(Database database);
    void onDatabaseUpdate(Database database);
    void onDatabaseRemove(Database database);
    void onDatabaseOpen(Database database);
    void onDatabaseClose(Database database);
    void onExit();
}
