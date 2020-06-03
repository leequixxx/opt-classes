package space.leequixxx.optclasses.data.repository.observer;

import space.leequixxx.optclasses.data.model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDatabaseCreateOnUpdateObserver implements UpdateEntityObserver<Database> {
    @Override
    public void onAction(Database entity) throws SQLException {
        String url = "jdbc:sqlite:" + entity.getPath();

        try (Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "surname VARCHAR(80) NOT NULL," +
                    "patronymic VARCHAR(60)," +
                    "group_number VARCHAR(10)," +
                    "average_mark UNSIGNED INTEGER(2) NOT NULL," +
                    "faculties VARCHAR(20)" +
                    ")");
            statement.close();
        }
    }
}
