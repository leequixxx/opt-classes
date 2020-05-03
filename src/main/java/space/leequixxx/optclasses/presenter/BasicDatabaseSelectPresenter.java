package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.ui.ConfirmationDialog;
import space.leequixxx.optclasses.ui.DatabaseCreate;
import space.leequixxx.optclasses.ui.view.DatabaseSelectView;

public class BasicDatabaseSelectPresenter implements DatabaseSelectPresenter {
    private DatabaseSelectView view;

    public BasicDatabaseSelectPresenter(DatabaseSelectView view) {
        this.view = view;
    }

    @Override
    public void createDatabase() {
        DatabaseCreate databaseCreate = new DatabaseCreate();
        int result = databaseCreate.showConfirmationDialog();

        if (result == ConfirmationDialog.OK_OPTION) {
        }
    }
}
