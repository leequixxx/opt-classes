package space.leequixxx.optclasses.locale;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class Language {
    private static final List<Language> languages = Arrays.asList(
            new EnglishLanguage(),
            new RussianLanguage(),
            new BelarusianLanguage()
    );

    public abstract Locale getLocale();
    public abstract String getName();

    public static List<Language> getAvailableLanguages() {
        return languages;
    }

    public static Language getLanguageByTag(String tag) {
        switch (tag) {
            case "en-US":
                return getAvailableLanguages().get(0);
            case "ru-RU":
                return getAvailableLanguages().get(1);
            case "be-BY":
                return getAvailableLanguages().get(2);
            default:
                return getAvailableLanguages().get(0);
        }
    }

    public static String getTagByLanguage(Language language) {
        if (language instanceof EnglishLanguage) {
            return "en-US";
        } else if (language instanceof RussianLanguage) {
            return "ru-RU";
        } else if (language instanceof BelarusianLanguage) {
            return "be-BY";
        }
        return "en-US";
    }

    @Override
    public String toString() {
        return getName();
    }
}
