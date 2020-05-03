package space.leequixxx.optclasses.data;

import space.leequixxx.optclasses.data.model.Database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Settings implements Serializable {
    private static Settings instance;
    private static String path;

    private final List<Database> databases = new ArrayList<>();
    private Locale locale = Locale.getDefault();

    private Settings() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("This is a singleton");
    }

    public static Settings getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Settings();

        return instance;
    }

    public static void setSaveLoadPath(String saveLoadPath) {
        path = saveLoadPath;
    }

    public static String getSaveLoadPath() {
        return path;
    }

    public static void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        instance = (Settings) ois.readObject();
        ois.close();
    }

    public static void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(instance);
        oos.flush();
        oos.close();
    }

    public static void loadOrCreate() throws IOException {
        try {
            load();
        } catch (Exception e) {
            getInstance();
            save();
        }
    }

    public List<Database> getDatabases() {
        return databases;
    }

    public Locale getLocale() {
        return locale;
    }
}
