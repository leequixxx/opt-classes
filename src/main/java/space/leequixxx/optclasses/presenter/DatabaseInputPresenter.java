package space.leequixxx.optclasses.presenter;

public interface DatabaseInputPresenter extends Presenter {
    void selectDatabasePath(DatabaseInputFileChoiceMode mode);
    void updateDatabaseNameByPath(String name, String path);
}
