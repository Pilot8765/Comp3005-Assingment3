import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

// Function used for Interacting with a Database
public class DatabaseFunctions {

    // Drop, Creates and Inserts Beginning data
    public static String createDB(Connection conn, String name) {
        try {
            // Drop Database
            String SQL = SQLCreators.dropDB(name);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);

            // Create Database
            SQL = SQLCreators.createDB(name);
            stmt.executeUpdate(SQL);

            // Drop Table -> Clear existing data Which may not have been deleted with database Drop
            SQL = SQLCreators.dropTable("Students");
            stmt.executeUpdate(SQL);
            SQL = SQLCreators.createStudentSchema();
            stmt.executeUpdate(SQL);

            // Initial Values
            String[][] students =
                    {{"John", "Doe", "john.doe@example.com", "2023-09-01"},
                            {"Jane", "Smith", "jane.smith@example.com", "2023-09-01"},
                            {"Jim", "Beam", "jim.beam@example.com", "2023-09-02"}};
            // Insert Initial Values
            for (int i = 0; i < students.length; i++){
                String[] student = students[i];
                SQL = SQLCreators.addStudent(student[0], student[1], student[2], student[3]);
                stmt.executeUpdate(SQL);
            }

            // Close statement
            stmt.close();
        }catch (SQLException e){
            System.out.println("Error Creating Database");
            System.out.println("Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        return name;
    }

    // Displays all students in the database
    public static void getAllStudents(Connection conn)  {
        String SQL = SQLCreators.getAllStudents();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            System.out.println("Student_Id, First_Name, Last_Name, Email, Enrollment_Date");
            while (rs.next()){
                int id = rs.getInt("student_id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String email = rs.getString("email");
                Date date = rs.getDate("enrollment_date");

                System.out.println(id + ", "+ fname + " " + lname + ", " + email + ", " + date.toString());
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Message: " + e.getMessage() + "\n");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // Add a Student to db
    public static void addStudent(Connection conn, String first_name, String last_name, String email, String date){
        String SQL = SQLCreators.addStudent(first_name, last_name, email, date);
        if (excuteUpdate(conn, SQL)){
            System.out.println("Successfully added Student");
        }
    }

    public static void updateStudentEmail(Connection conn, int student_id, String email){
        // Check if student exists in Database
        if (!checkForExistant(conn, student_id)){
            System.out.println("Student: " + student_id + " does not exist");
            return;
        }

        String SQL = SQLCreators.updateStudentEmail(student_id, email);
        if (excuteUpdate(conn, SQL)){
            System.out.println("Successfully Updated Email");
        }
    }

    // Deletes a Student from Database
    public static void deleteStudent(Connection conn, int student_id){

        // Check if student Exists in database
        if (!checkForExistant(conn, student_id)){
            System.out.println("Student: " + student_id + " does not exist");
            return;
        }

        String SQL = SQLCreators.deleteStudent(student_id);
        if (excuteUpdate(conn, SQL)){
            System.out.println("Successfully Removed Student");
        }
    }

    // Checks if a student exists in database
    // Used by Update and Remove
    public static boolean checkForExistant(Connection conn, int id){
        try{
            Statement stmt = conn.createStatement();
            String SQL = SQLCreators.selectStudent(id);
            ResultSet rs = stmt.executeQuery(SQL);
            boolean isIn = false;
            if(rs.next()){
                isIn = true;
            }
            rs.close();
            stmt.close();
            return isIn;
        }catch(SQLException e){
            System.out.println("Message: " + e.getMessage() + "\n");
        }
        return false;
    }

    // Runs a excuteUpdate statment
    public static boolean excuteUpdate(Connection conn, String SQL){
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            return true;
        }catch(SQLException e){
            System.out.println("Message: " + e.getMessage() + "\n");
            return false;
        }
    }
}