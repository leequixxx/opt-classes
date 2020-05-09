package space.leequixxx.optclasses.theme;

import com.github.weisj.darklaf.theme.SolarizedDarkTheme;
import com.github.weisj.darklaf.theme.Theme;

public class SolarizedDarkUiTheme extends UiTheme {
    @Override
    public String getName() {
        return "Solarized Dark";
    }

    @Override
    public Theme getTheme() {
        return new SolarizedDarkTheme();
    }
}
