package space.leequixxx.optclasses;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.theme.UiTheme;
import space.leequixxx.optclasses.ui.DatabaseSelectDialog;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        LafManager.install(UiTheme.getDefaultTheme().getTheme());

        loadSettingsOrExit();
        Settings settings = Settings.getInstance();

        Localization.getInstance().change(Language.getLanguageByTag(Settings.getInstance().getLanguage()));
        LafManager.install(UiTheme.getUiThemeByName(settings.getTheme()).getTheme());

        DatabaseSelectDialog dbSelect = new DatabaseSelectDialog();
        dbSelect.pack();
        dbSelect.setVisible(true);
    }

    private static void loadSettingsOrExit() {
        try {
            File programDataDirectory = new File(System.getProperty("user.home") + "/.optclasses");
            if (!programDataDirectory.exists() || !programDataDirectory.isDirectory()) {
                if (!programDataDirectory.mkdir()) {
                    throw new IOException("Failed to create .optclasses directory");
                }
            }
            Settings.setSaveLoadPath(programDataDirectory.getAbsolutePath() + "/" + "settings.oclss");
            Settings.loadOrCreate();
        } catch (IOException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(
                    frame,
                    "Failed to load or create settings file, please check permissions for program data folder.\r\n" + e.getLocalizedMessage(),
                    "Settings error",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }
}
