package space.leequixxx.optclasses.data.model;

import java.io.Serializable;

public class Database implements Serializable {
    public Database(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Database() {
    }

    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return name;
    }
}
