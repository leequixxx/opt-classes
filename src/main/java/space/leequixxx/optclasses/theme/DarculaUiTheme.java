package space.leequixxx.optclasses.theme;

import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.Theme;

public class DarculaUiTheme extends UiTheme {
    @Override
    public String getName() {
        return "Darcula";
    }

    @Override
    public Theme getTheme() {
        return new DarculaTheme();
    }
}
