package space.leequixxx.optclasses.presenter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import space.leequixxx.optclasses.ui.view.DatabaseCreateView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class BasicDatabaseCreatePresenter implements DatabaseCreatePresenter {
    private static final String FILE_EXTENSION = "oclsdb";

    private final DatabaseCreateView view;

    public BasicDatabaseCreatePresenter(DatabaseCreateView view) {
        this.view = view;
    }

    @Override
    public void selectDatabasePath() {
        JFrame fileChooserDialog = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Database path");
        fileChooser.setApproveButtonText("Create");
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
