package space.leequixxx.optclasses.ui.view;

import space.leequixxx.optclasses.data.model.Student;

public interface StudentsAndFacultiesView {
    void onExit();
    void onCloseDatabase();
    void updateStudentsTable();
    void createStudent(Student student);
}
