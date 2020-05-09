package space.leequixxx.optclasses.locale;

import java.util.Locale;

public class RussianLanguage extends Language {
    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag("ru-RU");
    }

    @Override
    public String getName() {
        return getLocale().getDisplayName();
    }
}
