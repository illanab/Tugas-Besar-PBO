import java.sql.*;
import java.util.*;


public class TaskManager implements TaskManagement {     // kelas TaskManager yang mengimplementasikan interface TaskManagement untuk mengelola tugas
    private Connection connection;
    private Scanner scanner;
    private List<Map<String, Object>> taskList; // Collection Frame
    //constructor
    public TaskManager() {
        try {                        
            // membuka koneksi ke database Mysql
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "");   //JDBC
            taskList = new ArrayList<>(); // Inisialisasi koleksi
        } catch (SQLException e) {       
             //exception handling jika terjadi kesalahan dalam koneksi ke database
            System.out.println("Kesalahan saat menghubungkan ke basis data: " + e.getMessage());
        }
        scanner = new Scanner(System.in);   //objek dari scanner
    }
    // implementasikan inputTask untuk memasukan tugas ke dalam databse
    @Override
    // input data atau CREAT
    public void inputTask() {
        try {                 //exception handling
            System.out.print("Masukan Nama Siswa: ");
            String name = scanner.nextLine();   //string methode 

            System.out.print("Masukan Kelas: ");
            String studentClass = scanner.nextLine();     //string methode 

            System.out.print("Masukan Mata Pelajaran: ");
            String subject = scanner.nextLine();      //string methode 

            System.out.print("Masukan Deskripsi Tugas (Terlambat/Tepat Waktu): ");
            String task = scanner.nextLine();     //string methode 

            // menginputakan date atau tanggal pengumpulan tugas
            System.out.print("Masukan Tanggal Pengumpulannya (YYYY-MM-DD): ");  
            String submissionDate = scanner.nextLine();     //string methode 

            // menentukan nilai sikap berdasarkan intugas
            int attitudeScore = 100;
            if (task.equalsIgnoreCase("Terlambat")) {               //percabangan
                attitudeScore -= 5;
            }

            // menyimpan data ke databse
            String query = "INSERT INTO tasks (name, class, subject, task, submission_date, attitude_score) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, studentClass);
            stmt.setString(3, subject);
            stmt.setString(4, task);
            stmt.setString(5, submissionDate);
            stmt.setInt(6, attitudeScore);
            stmt.executeUpdate();     // menjalankan query insert
           
           // Simpan ke koleksi untuk sementara
           Map<String, Object> newTask = new HashMap<>();
           newTask.put("name", name);
           newTask.put("class", studentClass);
           newTask.put("subject", subject);
           newTask.put("task", task);
           newTask.put("submission_date", submissionDate);
           newTask.put("attitude_score", attitudeScore);
           taskList.add(newTask);

            System.out.println("\"Tugas berhasil ditambahkan.");
        } catch (SQLException e) {                  
             //exception handling  jika terjadi kesalahan saat memasukan  tugas
            System.out.println("Kesalahan saat menambahkan tugas: " + e.getMessage());
        }
    }

    //implementasi viewtasks untuk melihat kesalahan saat memasukan tugas
    @Override
    // READ (Melihat Data)
    public void viewTasks() {
        try {                   //exception handling
            String query = "SELECT * FROM tasks";       // query untuk mengambil semua data tugas
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);     //menjalankan query dan mendapatlkan hasilnya

             // Kosongkan koleksi lama sebelum diisi ulang
             taskList.clear();

            while (rs.next()) {    //perulangan
                Map<String, Object> task = new HashMap<>();
                task.put("id", rs.getInt("id"));     //string methode 
                task.put("name", rs.getString("name"));     //string methode 
                task.put("class", rs.getString("class"));     //string methode 
                task.put("subject", rs.getString("subject"));     //string methode 
                task.put("task", rs.getString("task"));     //string methode 
                task.put("submission_date", rs.getDate("submission_date"));     //string methode menampilkan data tanggal
                task.put("attitude_score", rs.getInt("attitude_score"));     //string methode 
                taskList.add(task); // Simpan data ke koleksi
            }

            // Tampilkan dari koleksi
            for (Map<String, Object> task : taskList) {   // perulangan
                System.out.println("Task ID: " + task.get("id"));
                System.out.println("Name: " + task.get("name"));
                System.out.println("Class: " + task.get("class"));
                System.out.println("Subject: " + task.get("subject"));
                System.out.println("Task: " + task.get("task"));
                System.out.println("Submission Date: " + task.get("submission_date"));
                System.out.println("Attitude Score: " + task.get("attitude_score"));
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Kesalahan saat mengambil tugas: " + e.getMessage());
        }
    }

    //implementasi update task untuk memperbarui data tugas
    @Override
    // UPDATE (Memperbarui Data)
    public void updateTask() {
        try {                            //exception handling
            System.out.print("Masukkan ID Tugas untuk memperbarui: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Masukkan deskripsi tugas baru (Terlambat/Tepat Waktu): ");
            String task = scanner.nextLine();     //string methode input 

            //menghitung nilai sikap mahasiswa berdasarkan deskripsi
            int attitudeScore = 100;
            //percabangan
            if (task.equalsIgnoreCase("Terlambat")) {        //     //string methode equalsIgnoreCase membandingkan dua string tanpa memperhatikan huruf besar atau kecil            
                attitudeScore -= 5;
            }

            //query untuk memperbarui data tugas
            String query = "UPDATE tasks SET task = ?, attitude_score = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, task);     //string methode 
            stmt.setInt(2, attitudeScore);     //string methode 
            stmt.setInt(3, id);     //string methode 
            stmt.executeUpdate();      // menjalankan query update
            System.out.println("Tugas berhasil diperbarui.");
        } catch (SQLException e) {              //exception handling jika terjadi kesalaahan memperbaiki tugas
            System.out.println("Kesalahan saat memperbarui tugas: " + e.getMessage());
        }
    }
// implementasi delete berdasarkan id
    @Override
    // DELETE(Menghapus Data)
    public void deleteTask() {
        try {                 //exception handling meminta input id tugas yang akan di hapus
            System.out.print("Masukan ID Tugas Untuk menghapus: ");
            int id = scanner.nextInt();

            //query untuk menghapus tugas berdasarkan Id
            String query = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);     //string methode 
            stmt.executeUpdate();  // menjalankan query delete
            System.out.println("Tugas Berhasil Dihapus.");
        } catch (SQLException e) {                 //exception handling jika terjadi kesalahan saat menghapus tugas
            System.out.println("Kesalahan dalam Menghapus Tugas: " + e.getMessage());
        }
    }

    // Method untuk menghapus nilai rata" sikap siswa dari tabel nilai
    public void deleteAverageScore() {
        try {      ////exception handling
            // Meminta input nama siswa untuk menghapus nilai rata-rata
            System.out.print("Masukkan Nama Siswa untuk menghapus Nilai Rata-Rata: ");
            String name = scanner.nextLine();     //string methode input
    
            // Query untuk memeriksa apakah siswa dengan nama tersebut ada di tabel 'nilai'
            String checkQuery = "SELECT * FROM nilai WHERE name = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, name);     //string methode 
            ResultSet rs = checkStmt.executeQuery();
    
            // Jika nama siswa ditemukan, maka hapus nilai rata-rata
            if (rs.next()) {   // percabangan
                String deleteQuery = "DELETE FROM nilai WHERE name = ?";
                PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
                deleteStmt.setString(1, name);     //string methode 
                deleteStmt.executeUpdate(); // Menjalankan query delete
    
                System.out.println("Nilai rata-rata untuk " + name + " telah dihapus.");
            } else {    //percabangan
                // Jika nama siswa tidak ditemukan
                System.out.println("Data tidak valid. Tidak ada siswa dengan nama " + name + ".");
            }
        } catch (SQLException e) {          //exception handling jika terjadi kesalahan saat menghapus data
            // Menangani kesalahan jika terjadi error saat menghapus nilai rata-rata
            System.out.println("Terjadi kesalahan saat menghapus nilai rata-rata: " + e.getMessage());
        }
    }    
    public void closeConnection() {
        try {           //exception handling
            if (connection != null) connection.close();                  //percabangan
        } catch (SQLException e) {       //exception handling jika terjadi kesalahan saat menutup koneksi
            System.out.println("Kesalahan saat menutup koneksi basis data: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }

    // menghitung dan memperbarui nilai rata" sikap mahasiswa di database
    public void updateAverageScore(String name, double averageScore) {
        try {            //exception handling
           // menyimpan nilai rata" kedalam database
            String query = "INSERT INTO nilai (name, average_score) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);     //string methode 
            stmt.setDouble(2, averageScore);     //string methode 
            // menjalankan quaery insert
            stmt.executeUpdate();
            System.out.println("Nilai Rata-Rata " + name + " Sudah diperbarui.");
        } catch (SQLException e) {            //exception handling jika terjadi kesalahan sasat menyimpan nilai
            System.out.println("Kesalahan Dalam Memasukan Nilai Rata-Rata: " + e.getMessage());
        }
}
}