package pls;
import java.sql.*;
public class pls_work0 {
    private static final String DB_URL = "jdbc:mysql://localhost/task_tracker?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "asd87";

    public static void main(String[] args) {
        try {
            //createTables(); //создает таблицы для всех данных если таблиц нет
            //dropTables(); //удаляет вообще все данные из БД
            //addGroup("имя группы, такое чтобы не совпадало с существующим");
            //addStudent("имя студента, такое чтобы не совпадало с существующим","имя группы");
            //addTaskResult("имя студента","имя задания1",значение true/false(сдано/несдано));
            //addTaskResult("имя студента","имя задания2",значение);
            //addTaskResult("имя студента","имя задания3",значение);
            //removeGroup("имя группы");
            //removeStudent("имя студента");

            displayDatabase(); //для просмотра готового примера работы программы

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            System.out.println("----- Displaying Database Content -----");

            // Display groups
            ResultSet groupResultSet = statement.executeQuery("SELECT * FROM `groups`");
            System.out.println("Groups:");
            while (groupResultSet.next()) {
                System.out.println("Group ID: " + groupResultSet.getInt("id") + ", Group Name: " + groupResultSet.getString("name"));
            }
            System.out.println();

            // Display students
            ResultSet studentResultSet = statement.executeQuery("SELECT * FROM students");
            System.out.println("Students:");
            while (studentResultSet.next()) {
                System.out.println("Student ID: " + studentResultSet.getInt("id") +
                        ", Student Name: " + studentResultSet.getString("name") +
                        ", Group ID: " + studentResultSet.getInt("group_id"));
            }
            System.out.println();

            // Display task results
            ResultSet taskResultResultSet = statement.executeQuery("SELECT * FROM task_results");
            System.out.println("Task Results:");
            while (taskResultResultSet.next()) {
                System.out.println("Result ID: " + taskResultResultSet.getInt("id") +
                        ", Student ID: " + taskResultResultSet.getInt("student_id") +
                        ", Task Name: " + taskResultResultSet.getString("task_name") +
                        ", Result: " + taskResultResultSet.getBoolean("result"));
            }
        }
    }

    private static void createTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("USE task_tracker");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `groups` (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "group_id INT," +
                    "FOREIGN KEY (group_id) REFERENCES `groups`(id) ON DELETE CASCADE)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS task_results (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "student_id INT," +
                    "task_name VARCHAR(255) NOT NULL," +
                    "result BOOLEAN NOT NULL," +
                    "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE)");
        }
    }

    private static void dropTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS task_results");
            statement.executeUpdate("DROP TABLE IF EXISTS students");
            statement.executeUpdate("DROP TABLE IF EXISTS `groups`");
        }
    }

    private static void addGroup(String groupName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO `groups` (name) VALUES (?)")) {
            statement.setString(1, groupName);
            statement.executeUpdate();
        }
    }

    private static void removeGroup(String groupName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM `groups` WHERE name = ?")) {
            statement.setString(1, groupName);
            statement.executeUpdate();
        }
    }

    private static void addStudent(String studentName, String groupName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, group_id) VALUES (?, (SELECT id FROM `groups` WHERE name = ?))")) {
            statement.setString(1, studentName);
            statement.setString(2, groupName);
            statement.executeUpdate();
        }
    }

    private static void removeStudent(String studentName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE name = ?")) {
            statement.setString(1, studentName);
            statement.executeUpdate();
        }
    }

    private static void addTaskResult(String studentName, String taskName, boolean result) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO task_results (student_id, task_name, result) VALUES ((SELECT id FROM students WHERE name = ?), ?, ?)")) {
            statement.setString(1, studentName);
            statement.setString(2, taskName);
            statement.setBoolean(3, result);
            statement.executeUpdate();
        }
    }
}
