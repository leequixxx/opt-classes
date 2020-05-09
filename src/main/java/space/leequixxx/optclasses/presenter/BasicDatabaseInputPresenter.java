package space.leequixxx.optclasses.presenter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import space.leequixxx.optclasses.ui.view.DatabaseInputView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class BasicDatabaseInputPresenter implements DatabaseInputPresenter {
    private static final String FILE_EXTENSION = "oclsdb";

    private final DatabaseInputView view;
    private final ResourceBundle messageBundle;

    public BasicDatabaseInputPresenter(DatabaseInputView view) {
        this.view = view;
        this.messageBundle = ResourceBundle.getBundle("message_strings");
    }

    @Override
    public void selectDatabasePath(DatabaseInputFileChoiceMode mode) {
        JFrame fileChooserDialog = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        switch (mode) {
            case EDIT:
                fileChooser.setDialogTitle(messageBundle.getString("choice_database_edit_title"));
                break;
            case CREATE:
                fileChooser.setDialogTitle(messageBundle.getString("choice_database_create_title"));
                break;
        }
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("OptClasses database", FILE_EXTENSION));
        int selection = fileChooser.showSaveDialog(fileChooserDialog);

        if (selection == JFileChooser.APPROVE_OPTION) {
            File databaseFile = fileChooser.getSelectedFile();
            if (!databaseFile.getAbsolutePath().endsWith(".oclsdb")) {
                databaseFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + "." + FILE_EXTENSION);
            }
            this.view.setDatabasePath(databaseFile.getAbsolutePath());
        }
    }

    @Override
    public void updateDatabaseNameByPath(String name, String path) {
        if (!name.isEmpty()) {
            return;
        }

        File file = new File(path);
        String databaseName = StringUtils.removeEnd(file.getName(), "." + FILE_EXTENSION);
        databaseName = CaseUtils.toCamelCase(databaseName, true);

        view.setDatabaseName(databaseName);
    }
}
