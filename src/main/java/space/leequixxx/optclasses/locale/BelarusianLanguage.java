package space.leequixxx.optclasses.locale;

import java.util.Locale;

public class BelarusianLanguage extends Language {
    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag("be-BY");
    }

    @Override
    public String getName() {
        return getLocale().getDisplayName();
    }
}
