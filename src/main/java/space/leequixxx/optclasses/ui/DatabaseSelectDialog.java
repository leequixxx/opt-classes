package space.leequixxx.optclasses.ui;

import com.github.weisj.darklaf.listener.MouseClickListener;
import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.LocaleChangeListener;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.presenter.BasicDatabaseSelectPresenter;
import space.leequixxx.optclasses.presenter.DatabaseSelectPresenter;
import space.leequixxx.optclasses.ui.view.DatabaseSelectView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.*;
import java.util.*;

public class DatabaseSelectDialog extends JDialog implements DatabaseSelectView, LocaleChangeListener {
    private final DatabaseSelectPresenter presenter;
    private final DefaultListModel<Database> databaseListModel;

    private JPanel contentPane;
    private JList<Database> databasesList;
    private JButton openDatabaseButton;
    private JButton editDatabaseButton;
    private JButton deleteDatabaseButton;
    private JButton createDatabaseButton;
    private JButton exitButton;
    private JButton settingsButton;

    public DatabaseSelectDialog() {
        super();

        presenter = new BasicDatabaseSelectPresenter(this);
        databaseListModel = new DefaultListModel<>();

        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        Localization.getInstance().addLocaleChangeListener(this);
        onLocaleChange(Language.getLanguageByTag(Settings.getInstance().getLanguage()).getLocale());

        databasesList.setModel(databaseListModel);

        if (Settings.getInstance().getDatabase() != null) {
            presenter.openDatabase(Settings.getInstance().getDatabase());
        }

        bind();
    }

    private void syncDatabasesWithListModel(List<Database> databases) {
        databases.forEach(databaseListModel::addElement);
    }

    private void onExit(EventObject e) {
        presenter.exit();
    }

    private void onSettings(EventObject e) {
        presenter.settings();
    }

    private void onCreateDatabase(EventObject e) {
        presenter.createDatabase();
    }

    private void onEditDatabase(EventObject e) {
        if (!databasesList.isSelectionEmpty()) {
            presenter.editDatabase(databasesList.getSelectedValue());
        }
    }

    private void onDeleteDatabase(EventObject e) {
        if (!databasesList.isSelectionEmpty()) {
            presenter.deleteDatabase(databasesList.getSelectedValue());
        }
    }

    private void onOpenDatabase(EventObject e) {
        if (!databasesList.isSelectionEmpty()) {
            presenter.openDatabase(databasesList.getSelectedValue());
        }
    }

    private void onSelectDatabase(ListSelectionEvent e) {
        editDatabaseButton.setEnabled(true);
        deleteDatabaseButton.setEnabled(true);
        openDatabaseButton.setEnabled(true);
    }

    private void bind() {
        syncDatabasesWithListModel(Settings.getInstance().getDatabases());

        databasesList.registerKeyboardAction(this::onDeleteDatabase, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        databasesList.addMouseListener((MouseClickListener) mouseEvent -> {
            if (mouseEvent.getButton() == MouseEvent.BUTTON1 && mouseEvent.getClickCount() > 1) {
                onOpenDatabase(mouseEvent);
            }
        });
        createDatabaseButton.addActionListener(this::onCreateDatabase);
        editDatabaseButton.addActionListener(this::onEditDatabase);
        deleteDatabaseButton.addActionListener(this::onDeleteDatabase);
        openDatabaseButton.addActionListener(this::onOpenDatabase);
        exitButton.addActionListener(this::onExit);
        settingsButton.addActionListener(this::onSettings);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit(e);
            }
        });

        databasesList.addListSelectionListener(this::onSelectDatabase);
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

    @Override
    public void onDatabaseAdd(Database database) {
        databaseListModel.addElement(database);
    }

    @Override
    public void onDatabaseUpdate(Database database) {
        databasesList.updateUI();
    }

    @Override
    public void onDatabaseRemove(Database database) {
        databaseListModel.removeElement(database);
    }

    @Override
    public void onDatabaseOpen(Database database) {
        setVisible(false);
    }

    @Override
    public void onDatabaseClose(Database database) {
        setVisible(true);
    }

    @Override
    public void onExit() {
        setVisible(false);
    }
}
