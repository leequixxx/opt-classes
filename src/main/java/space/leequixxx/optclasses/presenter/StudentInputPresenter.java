package space.leequixxx.optclasses.presenter;

public interface StudentInputPresenter extends Presenter {
    void onNameUpdate(String name);
    void onSurnameUpdate(String surname);
    void onGroupUpdate(String group);
}
