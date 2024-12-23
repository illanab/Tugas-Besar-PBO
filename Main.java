import java.util.*;

public class Main {     //kelas utama untuk menjalankan aplikasi
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();    // objek
        Scanner scanner = new Scanner(System.in);
        ScoreCalculator scoreCalculator = new ScoreCalculator(scanner);
        int choice;

        // proses untuk log in
        String correctUsername = "Katherina";  // string methode Hardcoded username
        String correctPassword = "4567iop";  // string methode Hardcoded password
        boolean isLoggedIn = false;

        // loop hingga log in berhasil
        while (!isLoggedIn) {                       //perulangan
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();    //string methode
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();    //string methode

            // memeriksa kecocokan username dan password
            //percabangan
            if (username.equals(correctUsername) && password.equals(correctPassword)) {             //string methode equals membandingkan sua string dengan memperhatikan huruf besar dan kecil
                System.out.println("Login Berhasil!");     //string methode
                isLoggedIn = true;  // Exit the login loop
            } else {                          //percabangan
                System.out.println("Data Tidak Valid, Tolong Ulang Kembali.");    //string methode
            }
        }
        
        do {                //perulangan
            System.out.println("\nTask Management System");      //string methode
            System.out.println("1. Input Task");      //string methode
            System.out.println("2. View Tasks");      //string methode
            System.out.println("3. Update Task");      //string methode
            System.out.println("4. Delete Task");      //string methode
            System.out.println("5. Calculate Average Score");      //string methode
            System.out.println("6. Delete Average Score");      //string methode
            System.out.println("7. Exit");      //string methode
            System.out.print("Masukan Pilihanmu: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {                      //Percabangan
                case 1 -> taskManager.inputTask();
                case 2 -> taskManager.viewTasks();
                case 3 -> taskManager.updateTask();
                case 4 -> taskManager.deleteTask();
                case 5 -> {
                    System.out.print("Masukkan Nama Siswa untuk menghitung nilai rata-rata: ");
                    String name = scanner.nextLine();    //string methode
                    scoreCalculator.calculateAverageScore(name);
                }
                case 6 -> taskManager.deleteAverageScore(); // Call the delete method for average score
                case 7 -> taskManager.closeConnection();
                default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");    //string methode
            }
        } while (choice != 7);            //perulangan

        scanner.close();
    }
}