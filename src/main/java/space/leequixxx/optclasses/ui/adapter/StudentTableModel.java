package space.leequixxx.optclasses.ui.adapter;

import space.leequixxx.optclasses.data.model.Student;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class StudentTableModel implements TableModel  {
    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Student> students;

    public StudentTableModel(List<Student> students) {
        this.students = students;
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        ResourceBundle bundle = ResourceBundle.getBundle("tables/students_table");
        switch (columnIndex) {
            case 0: return bundle.getString("students_table_name");
            case 1: return bundle.getString("students_table_surname");
            case 2: return bundle.getString("students_table_patronymic");
            case 3: return bundle.getString("students_table_group_number");
            case 4: return bundle.getString("students_table_average_mark");
            default: return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex < 4) {
            return String.class;
        }
        return Integer.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return student.getName();
            case 1:
                return student.getSurname();
            case 2:
                return student.getPatronymic();
            case 3:
                return student.getGroupNumber();
            case 4:
                return student.getAverageMark();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch (columnIndex) {
            case 0:
                student.setName(value.toString());
                break;
            case 1:
                student.setSurname(value.toString());
                break;
            case 2:
                student.setPatronymic(value.toString());
                break;
            case 3:
                student.setGroupNumber(value.toString());
                break;
            case 4:
                student.setAverageMark(Integer.decode(value.toString()));
                break;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
        listeners.add(tableModelListener);
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {
        listeners.remove(tableModelListener);
    }

    public Student getValueAt(int rowIndex) {
        if (rowIndex == -1) {
            return null;
        }
        return this.students.get(rowIndex);
    }

    public void insertRow(Student student) {
        students.add(student);
    }
}
