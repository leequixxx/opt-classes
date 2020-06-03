package space.leequixxx.optclasses.data.model;

public class Student {
    public Student(int id, String name, String surname, String patronymic, String groupNumber, int averageMark, String faculties) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.groupNumber = groupNumber;
        this.averageMark = averageMark;
        this.faculties = faculties;
    }

    public Student(String name, String surname, String patronymic, String groupNumber, int averageMark, String faculties) {
        this(0, name, surname, patronymic, groupNumber, averageMark, faculties);
    }

    public Student(Student student, int id) {
        this(id, student.name, student.surname, student.patronymic, student.groupNumber, student.averageMark, student.faculties);
    }

    public Student() {
    }

    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String groupNumber;
    private int averageMark;
    private String faculties;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(int averageMark) {
        this.averageMark = averageMark;
    }

    public String getFaculties() {
        return faculties;
    }

    public void setFaculties(String faculties) {
        this.faculties = faculties;
    }
}
