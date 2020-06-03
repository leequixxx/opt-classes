package space.leequixxx.optclasses.ui;

import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.data.model.Student;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.LocaleChangeListener;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.presenter.BasicStudentsAndFacultiesPresenter;
import space.leequixxx.optclasses.presenter.DatabaseSelectPresenter;
import space.leequixxx.optclasses.presenter.StudentsAndFacultiesPresenter;
import space.leequixxx.optclasses.ui.adapter.StudentTableModel;
import space.leequixxx.optclasses.ui.view.StudentsAndFacultiesView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentsAndFacultiesFrame extends JFrame implements StudentsAndFacultiesView, LocaleChangeListener {
    private StudentsAndFacultiesPresenter presenter;

    private JPanel contentPane;
    private JTabbedPane facultiesTabs;
    private JTable studentsTable;
    private StudentTableModel studentTableModel;
    private TableRowSorter<StudentTableModel> studentTableSorter;
    private JTextField studentFilterTextField;
    private JLabel studentFilterLabel;
    private JButton createStudentButton;

    private JMenuBar bar;

    private JMenu appMenu;
    private JMenuItem settingsMenuItem;
    private JMenuItem closeDatabaseMenuItem;
    private JMenuItem quitMenuItem;

    private JMenu helpMenu;
    private JMenuItem aboutProgramMenuItem;

    private JPopupMenu studentPopupMenu;
    private JMenuItem editStudentMenuItem;
    private JMenuItem deleteStudentMenuItem;

    public StudentsAndFacultiesFrame(DatabaseSelectPresenter databaseSelectPresenter, Database database, Connection connection) {
        this.presenter = new BasicStudentsAndFacultiesPresenter(this, databaseSelectPresenter, database, connection);

        this.bindStudents();

        setContentPane(contentPane);
        setJMenuBar(createMenuBar());
        Localization.getInstance().addLocaleChangeListener(this);
        setMinimumSize(new Dimension(720, 480));
        setLocationRelativeTo(null);

        onLocaleChange(Language.getLanguageByTag(Settings.getInstance().getLanguage()).getLocale());
        bind();
    }

    private JMenuBar createMenuBar() {
        bar = new JMenuBar();

        bar.add(createAppMenu());
        bar.add(createHelpMenu());

        return bar;
    }

    private JMenu createAppMenu() {
        appMenu = new JMenu();
        settingsMenuItem = new JMenuItem();
        closeDatabaseMenuItem = new JMenuItem();
        quitMenuItem = new JMenuItem();

        appMenu.add(settingsMenuItem);
        appMenu.add(closeDatabaseMenuItem);
        appMenu.add(quitMenuItem);

        return appMenu;
    }

    private JMenu createHelpMenu() {
        helpMenu = new JMenu("Help");

        aboutProgramMenuItem = new JMenuItem("About program");

        helpMenu.add(aboutProgramMenuItem);

        return helpMenu;
    }

    private void settings(EventObject e) {
        presenter.settings();
    }

    private void close(EventObject e) {
        presenter.close();
    }

    private void exit(EventObject e) {
        presenter.exit();
    }

    private void about(EventObject e) {
        presenter.about();
    }

    private void bind() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit(e);
            }
        });
        settingsMenuItem.addActionListener(this::settings);
        closeDatabaseMenuItem.addActionListener(this::close);
        quitMenuItem.addActionListener(this::exit);
        aboutProgramMenuItem.addActionListener(this::about);
        createStudentButton.addActionListener(this::onCreteStudent);
    }

    private void bindStudents() {
        List<Student> students = presenter.getStudents();

        studentTableModel = new StudentTableModel(students);
        studentsTable.setModel(this.studentTableModel);

        studentTableSorter = new TableRowSorter<>(studentTableModel);
        studentTableSorter.setSortable(2, false);

        studentsTable.getTableHeader().setReorderingAllowed(false);
        studentsTable.setRowSorter(studentTableSorter);
        studentFilterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                update(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                update(documentEvent);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                update(documentEvent);
            }

            private void update(DocumentEvent documentEvent) {
                String text = studentFilterTextField.getText();
                if (text.trim().length() == 0) {
                    studentTableSorter.setRowFilter(null);
                } else {
                    studentTableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        createStudentPopup();
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentsTable.setComponentPopupMenu(studentPopupMenu);
    }

    private void createStudentPopup() {
        studentPopupMenu = new JPopupMenu("Student");
        editStudentMenuItem = new JMenuItem("Edit");
        deleteStudentMenuItem = new JMenuItem("Delete");
        studentPopupMenu.add(editStudentMenuItem);
        studentPopupMenu.add(deleteStudentMenuItem);

        editStudentMenuItem.addActionListener(actionEvent -> {
            if (studentsTable.getSelectedRow() != -1) {
                presenter.editStudent(studentTableModel.getValueAt(studentsTable.convertRowIndexToModel(studentsTable.getSelectedRow())));
            }
        });
    }

    private void onCreteStudent(EventObject e) {
        presenter.createStudent();
    }

    @Override
    public void onLocaleChange(Locale locale) {
        ResourceBundle globalBundle = ResourceBundle.getBundle("global_strings", locale);
        ResourceBundle menuBundle = ResourceBundle.getBundle("windows/students_and_faculties_menu_strings", locale);
        ResourceBundle windowBundle = ResourceBundle.getBundle("windows/students_and_faculties_strings");

        appMenu.setText(menuBundle.getString("app"));
        settingsMenuItem.setText(menuBundle.getString("settings"));
        closeDatabaseMenuItem.setText(menuBundle.getString("close_database"));
        quitMenuItem.setText(menuBundle.getString("quit"));

        helpMenu.setText(menuBundle.getString("help"));
        aboutProgramMenuItem.setText(menuBundle.getString("about_program"));
        studentFilterLabel.setText(globalBundle.getString("search"));

        for (int i = 0; i < studentsTable.getColumnCount(); i++) {
            studentsTable.getColumnModel().getColumn(i).setHeaderValue(studentTableModel.getColumnName(i));
        }
        studentsTable.getTableHeader().resizeAndRepaint();
    }

    @Override
    public void onExit() {
        setVisible(false);
    }

    @Override
    public void onCloseDatabase() {
        setVisible(false);
    }

    @Override
    public void updateStudentsTable() {
        studentsTable.updateUI();
    }

    @Override
    public void createStudent(Student student) {
        studentTableModel.insertRow(student);
    }
}
