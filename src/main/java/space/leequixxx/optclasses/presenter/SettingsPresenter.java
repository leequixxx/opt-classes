package space.leequixxx.optclasses.presenter;

import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.theme.UiTheme;

public interface SettingsPresenter extends Presenter {
    void onSaveSettings(UiTheme theme, Language language);
}
