package space.leequixxx.optclasses.locale;

import java.util.Locale;

public class EnglishLanguage extends Language {
    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag("en-US");
    }

    @Override
    public String getName() {
        return getLocale().getDisplayName();
    }
}
