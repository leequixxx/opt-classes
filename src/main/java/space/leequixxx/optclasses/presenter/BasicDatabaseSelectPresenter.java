package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.data.model.Database;
import space.leequixxx.optclasses.data.repository.SettingsDatabaseRepository;
import space.leequixxx.optclasses.data.repository.observer.SqliteDatabaseCreateOnAddObserver;
import space.leequixxx.optclasses.data.repository.observer.SqliteDatabaseCreateOnUpdateObserver;
import space.leequixxx.optclasses.ui.ConfirmationDialog;
import space.leequixxx.optclasses.ui.DatabaseInputDialog;
import space.leequixxx.optclasses.ui.SettingsDialog;
import space.leequixxx.optclasses.ui.StudentsAndFacultiesFrame;
import space.leequixxx.optclasses.ui.view.DatabaseSelectView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BasicDatabaseSelectPresenter implements DatabaseSelectPresenter {
    private final DatabaseSelectView view;
    private final SettingsDatabaseRepository repository;
    private final ResourceBundle messageBundle;

    public BasicDatabaseSelectPresenter(DatabaseSelectView view) {
        this.view = view;
        this.repository = new SettingsDatabaseRepository(Settings.getInstance());
        this.repository.addAddObserver(new SqliteDatabaseCreateOnAddObserver());
        this.repository.addUpdateObserver(new SqliteDatabaseCreateOnUpdateObserver());
        this.messageBundle = ResourceBundle.getBundle("message_strings");
    }

    @Override
    public void exit() {
        view.onExit();
        System.exit(0);
    }

    @Override
    public void settings() {
        SettingsDialog dialog = new SettingsDialog(Settings.getInstance());
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void createDatabase() {
        DatabaseInputDialog databaseInput = new DatabaseInputDialog(new Database(), DatabaseInputDialog.Mode.CREATE);
        int result = databaseInput.showConfirmationDialog();

        if (result == ConfirmationDialog.OK_OPTION) {
            try {
                File databaseFile = new File(databaseInput.getDatabase().getPath());
                if (databaseFile.exists()) {
                    JFrame frame = new JFrame();
                    int replace = JOptionPane.showConfirmDialog(
                            frame,
                            messageBundle.getString("choice_database_replace_text"),
                            messageBundle.getString("choice_database_replace_title"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (replace == JOptionPane.YES_OPTION) {
                        databaseFile.delete();
                    }
                }
                repository.add(databaseInput.getDatabase());
                view.onDatabaseAdd(databaseInput.getDatabase());
            } catch (Exception e) {
                saveSettingsError(e);
            }
        }
    }

    @Override
    public void editDatabase(Database database) {
        DatabaseInputDialog databaseInput = new DatabaseInputDialog(database, DatabaseInputDialog.Mode.EDIT);
        int result = databaseInput.showConfirmationDialog();

        if (result == ConfirmationDialog.OK_OPTION) {
            try {
                repository.update(database);
                view.onDatabaseUpdate(database);
            } catch (Exception e) {
                saveSettingsError(e);
            }
        }
    }

    @Override
    public void deleteDatabase(Database database) {
        JFrame frame = new JFrame();
        int result = JOptionPane.showConfirmDialog(
                frame,
                messageBundle.getString("delete_database_text"),
                messageBundle.getString("delete_database_title"),
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                repository.remove(database);
                view.onDatabaseRemove(database);
            } catch (Exception e) {
                saveSettingsError(e);
            }
        }
    }

    @Override
    public void openDatabase(Database database) {
        Settings.getInstance().setDatabase(database);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + database.getPath());
            Settings.save();
        } catch (IOException e) {
            saveSettingsError(e);
        } catch (SQLException e) {
            databaseError(e);
            return;
        }

        StudentsAndFacultiesFrame studentsAndFacultiesFrame = new StudentsAndFacultiesFrame(this, database, connection);
        studentsAndFacultiesFrame.pack();
        studentsAndFacultiesFrame.setVisible(true);
        this.view.onDatabaseOpen(database);
    }

    @Override
    public void closeDatabase(Database database, Connection connection) {
        Settings.getInstance().setDatabase(null);
        try {
            connection.close();
            Settings.save();
        } catch (IOException e) {
            saveSettingsError(e);
        } catch (SQLException e) {
            databaseError(e);
        }
        view.onDatabaseClose(database);
    }

    private void saveSettingsError(Exception e) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(
                frame,
                messageBundle.getString("save_settings_error_text") + "\r\n" + e.getLocalizedMessage(),
                messageBundle.getString("save_settings_error_title"),
                JOptionPane.ERROR_MESSAGE
        );
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
