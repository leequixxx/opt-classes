package space.leequixxx.optclasses.theme;

import com.github.weisj.darklaf.theme.OneDarkTheme;
import com.github.weisj.darklaf.theme.Theme;

public class OneDarkUiTheme extends UiTheme {
    @Override
    public String getName() {
        return "One Dark";
    }

    @Override
    public Theme getTheme() {
        return new OneDarkTheme();
    }
}
