package space.leequixxx.optclasses.ui;

import javax.swing.*;
import java.util.EventObject;

public class ConfirmationDialog extends JDialog {
    public static final int NONE_OPTION = 0;
    public static final int OK_OPTION = 1;
    public static final int CANCEL_OPTION = 2;

    protected int result = NONE_OPTION;

    public int showConfirmationDialog() {
        pack();
        setVisible(true);

        return result;
    }

    protected void onOk(EventObject e) {
        result = OK_OPTION;
        dispose();
    }

    protected void onCancel(EventObject e) {
        result = CANCEL_OPTION;
        dispose();
    }
}
