package space.leequixxx.optclasses.theme;

import com.github.weisj.darklaf.theme.SolarizedLightTheme;
import com.github.weisj.darklaf.theme.Theme;

public class SolarizedLightUiTheme extends UiTheme {
    @Override
    public String getName() {
        return "Solarized Light";
    }

    @Override
    public Theme getTheme() {
        return new SolarizedLightTheme();
    }
}
