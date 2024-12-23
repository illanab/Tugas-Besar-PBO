public class Student extends Person {      // subclass student dari class person
    String studentClass;

    //konstruktor untuk menginisiasi nama dan kelas
    public Student(String name, String studentClass) {
        super(name);
        this.studentClass = studentClass;
    }
    // getter untuk mendapatkan kelas siswa
    public String getStudentClass() {
        return studentClass;
    }
}