package space.leequixxx.optclasses.ui;

import com.github.weisj.darklaf.listener.UpdateDocumentListener;
import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Student;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.LocaleChangeListener;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.presenter.BasicStudentInputPresenter;
import space.leequixxx.optclasses.presenter.DatabaseInputFileChoiceMode;
import space.leequixxx.optclasses.presenter.StudentInputPresenter;
import space.leequixxx.optclasses.ui.view.StudentInputView;

import javax.swing.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentInputDialog extends ConfirmationDialog implements StudentInputView, LocaleChangeListener {
    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameTextField;
    private JLabel nameLabel;
    private JLabel groupLabel;
    private JTextField groupTextField;
    private JTextField patronymicTextField;
    private JLabel patronymicLabel;
    private JLabel surnameLabel;
    private JTextField surnameTextField;
    private JSlider markSlider;
    private JLabel markLabel;
    private JList facultiesList;
    private JLabel facultiesLabel;

    private StudentInputPresenter presenter;
    private Student student;
    private Mode mode;
    private ResourceBundle bundle;

    public StudentInputDialog(Student student, Mode mode) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.presenter = new BasicStudentInputPresenter(this, student);
        this.student = student;
        this.mode = mode;

        bind();

        Localization.getInstance().addLocaleChangeListener(this);
        onLocaleChange(Language.getLanguageByTag(Settings.getInstance().getLanguage()).getLocale());
    }

    private void bind() {
        okButton.addActionListener(this::onOk);

        cancelButton.addActionListener(this::onCancel);
        contentPane.registerKeyboardAction(this::onCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });

        nameTextField.getDocument().addDocumentListener((UpdateDocumentListener) this::onNameEdit);
        surnameTextField.getDocument().addDocumentListener((UpdateDocumentListener) this::onSurnameEdit);
        groupTextField.getDocument().addDocumentListener((UpdateDocumentListener) this::onGroupEdit);

        onNameEdit();
        onSurnameEdit();
        onGroupEdit();

        nameTextField.setText(student.getName());
        surnameTextField.setText(student.getSurname());
        patronymicTextField.setText(student.getPatronymic());
        groupTextField.setText(student.getGroupNumber());
        markSlider.setValue(student.getAverageMark());
        nameTextField.setText(student.getName());
    }

    private void onNameEdit() {
        presenter.onNameUpdate(nameTextField.getText());
    }

    private void onSurnameEdit() {
        presenter.onSurnameUpdate(surnameTextField.getText());
    }

    private void onGroupEdit() {
        presenter.onGroupUpdate(groupTextField.getText());
    }

    @Override
    public void onLocaleChange(Locale locale) {
        ResourceBundle titleBundle = ResourceBundle.getBundle("title_strings");
        ResourceBundle globalBundle = ResourceBundle.getBundle("global_strings");
        bundle = ResourceBundle.getBundle("windows/student_input_strings");

        nameLabel.setText(bundle.getString("name_label"));
        surnameLabel.setText(bundle.getString("surname_label"));
        patronymicLabel.setText(bundle.getString("patronymic_label"));
        groupLabel.setText(bundle.getString("group_label"));
        markLabel.setText(bundle.getString("mark_label"));
        facultiesLabel.setText(bundle.getString("faculties_label"));

        okButton.setText(globalBundle.getString("ok"));
        cancelButton.setText(globalBundle.getString("cancel"));

        if (mode == Mode.CREATE) {
            setTitle(titleBundle.getString("student_create"));
        } else {
            setTitle(titleBundle.getString("student_edit") + "(" + student.getName() + " " + student.getSurname()  + ")");
        }
    }

    @Override
    protected void onOk(EventObject e) {
        student.setName(nameTextField.getText());
        student.setSurname(surnameTextField.getText());
        student.setPatronymic(patronymicTextField.getText());
        student.setGroupNumber(groupTextField.getText());
        student.setAverageMark(markSlider.getValue());

        super.onOk(e);
    }

    @Override
    public void setOkButtonEnabled(boolean value) {
        okButton.setEnabled(value);
    }

    public Student getStudent() {
        return student;
    }

    public enum Mode {
        CREATE,
        EDIT;
    }
}
