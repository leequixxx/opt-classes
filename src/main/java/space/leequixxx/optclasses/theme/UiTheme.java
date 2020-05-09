package space.leequixxx.optclasses.theme;

import com.github.weisj.darklaf.theme.Theme;

import java.util.Arrays;
import java.util.List;

public abstract class UiTheme {
    private static final List<UiTheme> themes = Arrays.asList(
            new DarculaUiTheme(),
            new IntelliJUiTheme(),
            new OneDarkUiTheme(),
            new SolarizedDarkUiTheme(),
            new SolarizedLightUiTheme()
    );

    public abstract String getName();

    public abstract Theme getTheme();

    public static List<UiTheme> getAvailableThemes() {
        return themes;
    }

    public static UiTheme getDefaultTheme() {
        return new DarculaUiTheme();
    }

    public static UiTheme getUiThemeByName(String name) {
        switch (name) {
            case "Darcula":
                return getAvailableThemes().get(0);
            case "IntelliJ":
                return getAvailableThemes().get(1);
            case "OneDark":
                return getAvailableThemes().get(2);
            case "SolarizedDark":
                return getAvailableThemes().get(3);
            case "SolarizedLight":
                return getAvailableThemes().get(4);
            default:
                return getAvailableThemes().get(1);
        }
    }

    public static String getNameByUiTheme(UiTheme theme) {
        if (theme instanceof DarculaUiTheme) {
            return "Darcula";
        } else if (theme instanceof OneDarkUiTheme) {
            return "OneDark";
        } else if (theme instanceof IntelliJUiTheme) {
            return "IntelliJ";
        } else if (theme instanceof SolarizedDarkUiTheme) {
            return "SolarizedDark";
        } else if (theme instanceof SolarizedLightUiTheme) {
            return "SolarizedLight";
        }
        return "IntelliJ";
    }

    @Override
    public String toString() {
        return getName();
    }
}
