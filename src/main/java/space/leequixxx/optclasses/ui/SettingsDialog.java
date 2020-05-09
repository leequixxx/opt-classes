package space.leequixxx.optclasses.ui;

import space.leequixxx.optclasses.data.Settings;
import space.leequixxx.optclasses.locale.Language;
import space.leequixxx.optclasses.locale.LocaleChangeListener;
import space.leequixxx.optclasses.locale.Localization;
import space.leequixxx.optclasses.presenter.BasicSettingsPresenter;
import space.leequixxx.optclasses.presenter.SettingsPresenter;
import space.leequixxx.optclasses.theme.UiTheme;
import space.leequixxx.optclasses.ui.view.SettingsView;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class SettingsDialog extends ConfirmationDialog implements SettingsView, LocaleChangeListener {
    SettingsPresenter presenter;
    Settings settings;

    DefaultComboBoxModel<UiTheme> themesListModel;
    DefaultComboBoxModel<Language> languagesListModel;

    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JComboBox<Language> languageComboBox;
    private JLabel languageLabel;
    private JComboBox<UiTheme> themeComboBox;
    private JLabel themeLabel;

    public SettingsDialog(Settings settings) {
        super();

        this.presenter = new BasicSettingsPresenter();
        this.settings = settings;

        themesListModel = new DefaultComboBoxModel<>();
        languagesListModel = new DefaultComboBoxModel<>();

        themeComboBox.setModel(themesListModel);
        languageComboBox.setModel(languagesListModel);


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        Localization.getInstance().addLocaleChangeListener(this);
        onLocaleChange(Language.getLanguageByTag(Settings.getInstance().getLanguage()).getLocale());

        bind();
    }

    private void syncUiThemesWithComboBox(List<UiTheme> themes) {
        themes.forEach(themesListModel::addElement);
        themesListModel.setSelectedItem(UiTheme.getUiThemeByName(Settings.getInstance().getTheme()));
    }

    private void syncLocalesWithComboBox(List<Language> languages) {
        languages.forEach(languagesListModel::addElement);
        languagesListModel.setSelectedItem(Language.getLanguageByTag(Settings.getInstance().getLanguage()));
    }

    @Override
    protected void onOk(EventObject e) {
        presenter.onSaveSettings((UiTheme) themesListModel.getSelectedItem(), (Language) languagesListModel.getSelectedItem());
        super.onOk(e);
    }

    private void bind() {
        syncUiThemesWithComboBox(UiTheme.getAvailableThemes());
        syncLocalesWithComboBox(Language.getAvailableLanguages());

        okButton.addActionListener(this::onOk);
        cancelButton.addActionListener(this::onCancel);
        contentPane.registerKeyboardAction(
                this::onCancel,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });
    }

    @Override
    public void onLocaleChange(Locale locale) {
        ResourceBundle titleBundle = ResourceBundle.getBundle("title_strings", locale);
        ResourceBundle globalBundle = ResourceBundle.getBundle("global_strings", locale);
        ResourceBundle settingsBundle = ResourceBundle.getBundle("windows/settings_strings", locale);

        setTitle(titleBundle.getString("settings"));
        themeLabel.setText(settingsBundle.getString("theme"));
        languageLabel.setText(settingsBundle.getString("language"));

        okButton.setText(globalBundle.getString("ok"));
        cancelButton.setText(globalBundle.getString("cancel"));
    }
}
