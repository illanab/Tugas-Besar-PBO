import java.util.Scanner;

public class ScoreCalculator {  // class untung menghitung nilai rata" siswa
    private Scanner scanner;

    public ScoreCalculator(Scanner scanner) {
        this.scanner = scanner;
    }
    //metode menghitung nilai rata" inputan nilai sikap siswa
    public void calculateAverageScore(String name) {
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        double totalScore = 0;
        // perulangan untuk mengambil nilai setiap mata pelajaran
        for (int i = 0; i < numSubjects; i++) {       
            System.out.print("Enter score for subject " + (i + 1) + ": ");
            totalScore += scanner.nextDouble();
        }
        //menghitung nilai sikap siswa rata" 
        double averageScore = totalScore / numSubjects;
        System.out.println("Average score for " + name + ": " + averageScore);

        //  memperbarui nilai rata" kedalam database
        TaskManager taskManager = new TaskManager();
        taskManager.updateAverageScore(name, averageScore);
    }
}