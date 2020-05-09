package space.leequixxx.optclasses.theme;

import com.github.weisj.darklaf.theme.IntelliJTheme;
import com.github.weisj.darklaf.theme.Theme;

public class IntelliJUiTheme extends UiTheme {
    @Override
    public String getName() {
        return "IntelliJ";
    }

    @Override
    public Theme getTheme() {
        return new IntelliJTheme();
    }
}
