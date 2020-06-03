package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.data.model.Student;

import java.util.List;

public interface StudentsAndFacultiesPresenter extends Presenter {
    void settings();
    void close();
    void exit();
    void about();

    void editStudent(Student student);
    void createStudent();

    List<Student> getStudents();
}
