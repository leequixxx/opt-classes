package space.leequixxx.optclasses.ui;

import org.apache.commons.text.CaseUtils;
import space.leequixxx.optclasses.presenter.BasicDatabaseCreatePresenter;
import space.leequixxx.optclasses.presenter.DatabaseCreatePresenter;
import space.leequixxx.optclasses.ui.view.DatabaseCreateView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

public class DatabaseCreate extends ConfirmationDialog implements DatabaseCreateView {
    private DatabaseCreatePresenter presenter;

    private JPanel contentPane;
    protected JButton okButton;
    protected JButton cancelButton;
    private JTextField databasePathTextField;
    private JButton openDatabaseButton;
    private JTextField databaseNameTextField;

    public DatabaseCreate() {
        super();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(ResourceBundle.getBundle("title_strings", Locale.getDefault()).getString("database_create"));

        presenter = new BasicDatabaseCreatePresenter(this);
        bind();
    }

    private void onOpenDatabase(EventObject e) {
        presenter.selectDatabasePath();
    }

    private void onDatabasePath(DocumentEvent e) {
        presenter.updateDatabaseNameByPath(databaseNameTextField.getText(), databasePathTextField.getText());
    }

    private void bind() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });
        contentPane.registerKeyboardAction(this::onCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        okButton.addActionListener(this::onOk);
        cancelButton.addActionListener(this::onCancel);
        openDatabaseButton.addActionListener(this::onOpenDatabase);
        databasePathTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                onDatabasePath(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                onDatabasePath(documentEvent);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                onDatabasePath(documentEvent);
            }
        });
    }

    @Override
    public void setDatabasePath(String path) {
        databasePathTextField.setText(path);
    }

    @Override
    public void setDatabaseName(String name) {
        databaseNameTextField.setText(name);
    }

}
