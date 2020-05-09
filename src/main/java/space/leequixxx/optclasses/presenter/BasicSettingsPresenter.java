package space.leequixxx.optclasses.presenter;

import com.github.weisj.darklaf.LafManager;
import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.theme.UiTheme;

import javax.swing.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class BasicSettingsPresenter implements SettingsPresenter {
    ResourceBundle messageBundle;

    public BasicSettingsPresenter() {
        this.messageBundle = ResourceBundle.getBundle("message_strings");
    }

    @Override
    public void onSaveSettings(UiTheme theme, Language language) {
        Settings settings = Settings.getInstance();
        settings.setTheme(UiTheme.getNameByUiTheme(theme));
        settings.setLanguage(Language.getTagByLanguage(language));

        try {
            Settings.save();
        } catch (IOException e) {
            saveSettingsError(e);
        }

        LafManager.install(theme.getTheme());
        Localization.getInstance().change(language);
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
}
