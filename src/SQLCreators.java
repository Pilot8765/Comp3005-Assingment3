
// Functions used for creating SQL Querries
public class SQLCreators {

    // Returns a string used to Drop a database
    public static String dropDB(String name){
        return "DROP DATABASE IF EXISTS " + name;
    }

    // Returns a string used to Create a database
    public static String createDB(String name){
        return "CREATE DATABASE " + name;
    }

    public static String dropTable(String name){
        return "DROP TABLE IF EXISTS " + name;
    }

    // Create the Student schema for database
    public static String createStudentSchema() {
        return "CREATE TABLE IF NOT EXISTS Students "
                +"(student_id SERIAL, " +
                "first_name TEXT NOT NULL, " +
                "last_name TEXT Not NULL, " +
                "email TEXT NOT NULL UNIQUE, " +
                "enrollment_date DATE, " +
                " primary key (student_id) )";
    }

    // Returns a string used to Select all columns from the Students table
    public static String getAllStudents(){
        return "Select * From Students";
    }

    // Returns a string to insert a specified student into a database
    public static String addStudent(String first_name, String last_name, String email, String date){
        return "INSERT INTO Students (first_name, last_name, email, enrollment_date) VALUES ('" + first_name +"', '" + last_name + "', '" + email + "', '" + date + "')";
    }

    // Returns a string to update the email of a student in the database
    public static String updateStudentEmail(int student_id, String email){
        return "UPDATE Students SET email = '" + email + "' WHERE student_id = " + student_id;
    }

    // Returns a string to delete a student from the database
    public static String deleteStudent(int student_id){
        return "DELETE FROM Students WHERE student_id = " + student_id;
    }

    // selects a specific Student
    public static String selectStudent(int id){
        return "SELECT FROM Students WHERE student_id = " + id;
    }
}
