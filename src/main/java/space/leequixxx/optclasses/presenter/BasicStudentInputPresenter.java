package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.data.model.Student;
import space.leequixxx.optclasses.ui.view.StudentInputView;

public class BasicStudentInputPresenter implements StudentInputPresenter {
    private StudentInputView view;
    private Student student;

    boolean isNameEmpty = true;
    boolean isSurnameEmpty = true;
    boolean isGroupEmpty = true;

    public BasicStudentInputPresenter(StudentInputView view, Student student) {
        this.view = view;
        this.student = student;
    }

    @Override
    public void onNameUpdate(String name) {
        isNameEmpty = name.isEmpty();
        onEmptyDisableOk();
    }

    @Override
    public void onSurnameUpdate(String surname) {
        isSurnameEmpty = surname.isEmpty();
        onEmptyDisableOk();
    }

    @Override
    public void onGroupUpdate(String group) {
        isGroupEmpty = group.isEmpty();
        onEmptyDisableOk();
    }

    public void onEmptyDisableOk() {
        if (!isNameEmpty && !isSurnameEmpty && !isGroupEmpty) {
            view.setOkButtonEnabled(true);
        } else {
            view.setOkButtonEnabled(false);
        }
    }
}
