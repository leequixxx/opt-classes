package space.leequixxx.optclasses.data.repository;

import space.leequixxx.optclasses.data.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteStudentRepository implements StudentRepository {
    private Connection connection;

    public SqliteStudentRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student get(String id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ? LIMIT 1");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            return fromResultSet(rs);
        }
        return null;
    }

    @Override
    public List<Student> all() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM students");

        List<Student> students = new ArrayList<>();

        while (rs.next()) {
            students.add(fromResultSet(rs));
        }

        return students;
    }

    @Override
    public Student add(Student entity) throws Exception {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, surname, patronymic, group_number, average_mark) VALUES (?, ?, ?, ?, ?)");
        toStatement(statement, entity);
        statement.executeUpdate();

        Statement idStatement = connection.createStatement();
        ResultSet rs = idStatement.executeQuery("SELECT last_insert_rowid() as id");

        if (rs.next()) {
            return new Student(entity, rs.getInt("id"));
        }
        return null;
    }

    @Override
    public void remove(Student entity) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
        statement.setInt(1, entity.getId());

        statement.executeQuery();
    }

    @Override
    public Student update(Student entity) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE students SET name = ?, surname = ?, patronymic = ?, group_number = ?, average_mark = ?, faculties = ? WHERE id = ?");
        toStatement(statement, entity);

        statement.setInt(7, entity.getId());

        statement.executeUpdate();

        return entity;
    }

    private Student fromResultSet(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("patronymic"),
                rs.getString("group_number"),
                rs.getInt("average_mark"),
                rs.getString("faculties")
        );
    }

    private void toStatement(PreparedStatement statement, Student entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPatronymic());
        statement.setString(4, entity.getGroupNumber());
        statement.setInt(5, entity.getAverageMark());
        statement.setString(6, entity.getFaculties());
    }
}
