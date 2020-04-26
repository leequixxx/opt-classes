package space.leequixxx.optclasses;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import space.leequixxx.optclasses.view.DatabaseSelect;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        LafManager.install(new DarculaTheme());
        DatabaseSelect dbSelect = new DatabaseSelect();
        dbSelect.pack();
        dbSelect.setVisible(true);
    }
}
