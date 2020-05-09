package space.leequixxx.optclasses.ui;

import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.LocaleChangeListener;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.presenter.BasicDatabaseInputPresenter;
import space.leequixxx.optclasses.presenter.DatabaseInputFileChoiceMode;
import space.leequixxx.optclasses.presenter.DatabaseInputPresenter;
import space.leequixxx.optclasses.ui.view.DatabaseInputView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

public class DatabaseInputDialog extends ConfirmationDialog implements DatabaseInputView, LocaleChangeListener {
    private DatabaseInputPresenter presenter;
    private Database database;
    private Mode mode;
    private ResourceBundle titleBundle;

    private JPanel contentPane;
    protected JButton okButton;
    protected JButton cancelButton;
    private JTextField databasePathTextField;
    private JButton openDatabaseButton;
    private JTextField databaseNameTextField;
    private JLabel databaseNameLabel;
    private JLabel databasePathLabel;

    public enum Mode {
        CREATE,
        EDIT;

        public DatabaseInputFileChoiceMode toDatabaseInputFileChoiceMode() {
            switch (this) {
                case EDIT:
                    return DatabaseInputFileChoiceMode.EDIT;
                case CREATE:
                    return DatabaseInputFileChoiceMode.CREATE;
                default:
                    return null;
            }
        }
    }

    public DatabaseInputDialog(Database database, Mode mode) {
        super();

        this.titleBundle = ResourceBundle.getBundle("title_strings");
        this.presenter = new BasicDatabaseInputPresenter(this);
        this.database = database;
        this.mode = mode;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Settings settings = Settings.getInstance();
        Localization.getInstance().addLocaleChangeListener(this);
        onLocaleChange(Language.getLanguageByTag(settings.getLanguage()).getLocale());

        this.databaseNameTextField.setText(database.getName());
        this.databasePathTextField.setText(database.getPath());

        bind();
    }

    @Override
    protected void onOk(EventObject e) {
        database.setName(databaseNameTextField.getText());
        database.setPath(databasePathTextField.getText());

        super.onOk(e);
    }

    private void onOpenDatabase(EventObject e) {
        presenter.selectDatabasePath(mode.toDatabaseInputFileChoiceMode());
    }

    private void onDatabasePath(DocumentEvent e) {
        presenter.updateDatabaseNameByPath(databaseNameTextField.getText(), databasePathTextField.getText());
    }

    private void bind() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });
        contentPane.registerKeyboardAction(this::onCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        okButton.addActionListener(this::onOk);
        cancelButton.addActionListener(this::onCancel);
        openDatabaseButton.addActionListener(this::onOpenDatabase);
        databasePathTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                onDatabasePath(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                onDatabasePath(documentEvent);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                onDatabasePath(documentEvent);
            }
        });
    }

    @Override
    public void onLocaleChange(Locale locale) {
        ResourceBundle titleBundle = ResourceBundle.getBundle("title_strings", locale);
        ResourceBundle globalBundle = ResourceBundle.getBundle("global_strings", locale);
        ResourceBundle databaseInputBundle = ResourceBundle.getBundle("windows/database_input_strings", locale);

        switch (mode) {
            case EDIT:
                setTitle(titleBundle.getString("database_edit"));
                break;
            case CREATE:
                setTitle(titleBundle.getString("database_create"));
                break;
        }

        databaseNameLabel.setText(databaseInputBundle.getString("database_name"));
        databasePathLabel.setText(databaseInputBundle.getString("database_path"));

        okButton.setText(globalBundle.getString("ok"));
        cancelButton.setText(globalBundle.getString("cancel"));
    }

    @Override
    public void setDatabasePath(String path) {
        databasePathTextField.setText(path);
    }

    @Override
    public void setDatabaseName(String name) {
        databaseNameTextField.setText(name);
    }

    public Database getDatabase() {
        return database;
    }
}
