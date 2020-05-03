package space.leequixxx.optclasses.locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Localization {
    private static Localization instance;

    private List<LocaleChangeListener> listeners;

    private Localization() {
        listeners = new ArrayList<>();
    }

    public static Localization getInstance() {
        if (instance == null) {
            instance = new Localization();
        }
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("This is a singleton");
    }

    public void change(Locale locale) {
        Locale.setDefault(locale);

        listeners.forEach(listener -> {
            listener.onLocaleChange(locale);
        });
    }

    public void addLocaleChangeListener(LocaleChangeListener listener) {
        listeners.add(listener);
    }

    public void removeLocaleChangeListener(LocaleChangeListener listener) {
        listeners.remove(listener);
    }
}
