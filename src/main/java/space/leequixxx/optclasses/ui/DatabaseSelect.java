package space.leequixxx.optclasses.ui;

import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.locale.LocaleChangeListener;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.presenter.BasicDatabaseSelectPresenter;
import space.leequixxx.optclasses.presenter.DatabaseSelectPresenter;
import space.leequixxx.optclasses.ui.view.DatabaseSelectView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class DatabaseSelect extends JDialog implements DatabaseSelectView, LocaleChangeListener {
    private DatabaseSelectPresenter presenter;

    private JPanel contentPane;
    private JList<Database> databasesList;
    private JButton openDatabaseButton;
    private JButton editDatabaseButton;
    private JButton deleteDatabaseButton;
    private JButton createDatabaseButton;
    private JButton exitButton;
    private JButton settingsButton;

    public DatabaseSelect() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        Localization.getInstance().addLocaleChangeListener(this);
        onLocaleChange(Settings.getInstance().getLocale());

        Database[] databases = new Database[Settings.getInstance().getDatabases().size()];
        for (int i = 0; i < Settings.getInstance().getDatabases().size(); i++) {
            databases[i] = Settings.getInstance().getDatabases().get(i);
        }
        databasesList.setListData(databases);

        presenter = new BasicDatabaseSelectPresenter(this);
        bind();
    }

    private void onExit(EventObject e) {
        this.setVisible(false);
        System.exit(0);
    }

    private void onSettings(EventObject e) {
        Localization.getInstance().change(Locale.ENGLISH);
    }

    private void onCreateDatabase(EventObject e) {
        presenter.createDatabase();
    }

    private void bind() {
        this.createDatabaseButton.addActionListener(this::onCreateDatabase);
        this.exitButton.addActionListener(this::onExit);
        this.settingsButton.addActionListener(this::onSettings);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit(e);
            }
        });
    }

    @Override
    public void onLocaleChange(Locale locale) {
        ResourceBundle titleBundle = ResourceBundle.getBundle("title_strings", locale);
        ResourceBundle globalBundle = ResourceBundle.getBundle("global_strings", locale);

        setTitle(titleBundle.getString("database_select"));
        openDatabaseButton.setText(globalBundle.getString("open"));
        editDatabaseButton.setText(globalBundle.getString("edit"));
        deleteDatabaseButton.setText(globalBundle.getString("delete"));
        createDatabaseButton.setText(globalBundle.getString("create"));
        settingsButton.setText(globalBundle.getString("settings"));
        exitButton.setText(globalBundle.getString("exit"));
    }
}
