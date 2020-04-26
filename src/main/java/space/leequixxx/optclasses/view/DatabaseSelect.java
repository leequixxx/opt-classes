package space.leequixxx.optclasses.view;

import rx.observables.SwingObservable;

import javax.swing.*;

public class DatabaseSelect extends JDialog {
    private JPanel contentPane;
    private JList databasesList;
    private JButton openDatabaseButton;
    private JButton editDatabaseButton;
    private JButton deleteDatabaseButton;
    private JButton createDatabaseButton;
    private JButton exitButton;
    private JButton settingsButton;

    public DatabaseSelect() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        SwingObservable.fromButtonAction(exitButton).subscribe(e -> System.exit(0));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
