package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.data.model.Student;
import space.leequixxx.optclasses.data.repository.SqliteStudentRepository;
import space.leequixxx.optclasses.data.repository.StudentRepository;
import space.leequixxx.optclasses.ui.ConfirmationDialog;
import space.leequixxx.optclasses.ui.SettingsDialog;
import space.leequixxx.optclasses.ui.StudentInputDialog;
import space.leequixxx.optclasses.ui.StudentsAndFacultiesFrame;
import space.leequixxx.optclasses.ui.view.StudentsAndFacultiesView;

import javax.swing.*;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class BasicStudentsAndFacultiesPresenter implements StudentsAndFacultiesPresenter {
    private StudentsAndFacultiesView view;
    private ResourceBundle messageBundle;
    private DatabaseSelectPresenter databaseSelectPresenter;
    private Database database;
    private Connection connection;
    private StudentRepository studentRepository;

    public BasicStudentsAndFacultiesPresenter(StudentsAndFacultiesView view, DatabaseSelectPresenter databaseSelectPresenter, Database database, Connection connection) {
        this.view = view;
        this.databaseSelectPresenter = databaseSelectPresenter;
        this.messageBundle = ResourceBundle.getBundle("message_strings");
        this.database = database;
        this.connection = connection;
        this.studentRepository = new SqliteStudentRepository(connection);
    }

    @Override
    public void settings() {
        SettingsDialog dialog = new SettingsDialog(Settings.getInstance());
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void close() {
        view.onCloseDatabase();
        databaseSelectPresenter.closeDatabase(database, connection);
    }

    @Override
    public void exit() {
        view.onExit();
        System.exit(0);
    }

    @Override
    public void about() {
        ResourceBundle aboutBundle = ResourceBundle.getBundle("about_strings");

        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(
                frame,
                aboutBundle.getString("about_text") + "\r\n\r\n" + aboutBundle.getString("about_copyleft"),
                aboutBundle.getString("about_title"),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void editStudent(Student student) {
        StudentInputDialog studentInput = new StudentInputDialog(student, StudentInputDialog.Mode.EDIT);
        studentInput.setLocationRelativeTo((StudentsAndFacultiesFrame) view);
        int result = studentInput.showConfirmationDialog();

        if (result == ConfirmationDialog.OK_OPTION) {
            try {
                studentRepository.update(studentInput.getStudent());
                view.updateStudentsTable();
            } catch (Exception e) {
                databaseError(e);
            }
        }
    }

    @Override
    public void createStudent() {
        StudentInputDialog studentInput = new StudentInputDialog(new Student(), StudentInputDialog.Mode.CREATE);
        studentInput.setLocationRelativeTo((StudentsAndFacultiesFrame) view);
        int result = studentInput.showConfirmationDialog();

        if (result == ConfirmationDialog.OK_OPTION) {
            try {
                Student student = studentRepository.add(studentInput.getStudent());
                view.createStudent(student);
            } catch (Exception e) {
                databaseError(e);
            }
        }
    }

    @Override
    public List<Student> getStudents() {
        try {
            return this.studentRepository.all();
        } catch (Exception e) {
            databaseError(e);
            return Collections.emptyList();
        }
    }

    private void databaseError(Exception e) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(
                frame,
                messageBundle.getString("database_error_text") + "\r\n" + e.getLocalizedMessage(),
                messageBundle.getString("database_error_title"),
                JOptionPane.ERROR_MESSAGE
        );
    }
}
