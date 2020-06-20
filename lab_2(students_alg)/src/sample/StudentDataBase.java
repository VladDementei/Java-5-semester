package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class StudentDataBase {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_NAME = "lab_2";
    private static final String TABLE_NAME = "students";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    private Connection connection = null;
    private Statement statement = null;

    public StudentDataBase() throws ClassNotFoundException{
        try {
            Class.forName(JDBC_DRIVER);
            reconnect();
        } catch (SQLException se) {
            try {
                this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
                this.statement = connection.createStatement();
                this.statement.executeUpdate("CREATE DATABASE " + DB_NAME);
                System.out.println("Database " + DB_NAME + " created successfully");
                reconnect();
                createTable();
            } catch (SQLException e) {
                System.out.println("Create database error");
            }
        }
    }

    private void reconnect() throws SQLException{
        System.out.println("Connecting to database " + DB_NAME);
        this.connection = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
        this.statement = this.connection.createStatement();
    }

    public void createTable() throws SQLException{

        System.out.println("Drop table");
        this.statement.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);

        System.out.println("Creating table " + TABLE_NAME);
        String sql = "CREATE TABLE " + TABLE_NAME +
                "(id VARCHAR(255) not NULL, " +
                " surname VARCHAR(255), " +
                " group_num INTEGER, " +
                " average_mark double precision, " +
                " mark_math INTEGER, " +
                " mark_ep INTEGER, " +
                " PRIMARY KEY ( id ))";
        this.statement.executeUpdate(sql);
        System.out.println("Table '" + TABLE_NAME + "' created successfully");
    }

    public ArrayList<Student> readStudents() throws SQLException{
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT surname, group_num, mark_math, mark_ep FROM " + TABLE_NAME;
        System.out.println(sql);
        try (ResultSet resultSet = this.statement.executeQuery(sql)){
            while (resultSet.next()) {
                students.add(new Student(resultSet.getString("surname"), resultSet.getInt("group_num"),
                        resultSet.getInt("mark_math"), resultSet.getInt("mark_ep")));
            }
        }
        return students;
    }

    public void writeStudents(ArrayList<GroupInfo> students) throws SQLException{
        System.out.println("Rewrite data into table");
        this.statement.executeUpdate("DELETE FROM " + TABLE_NAME);

        for (GroupInfo groupInfo : students) {
            if(groupInfo instanceof Student) {
                String sql = String.format(Locale.US, "INSERT INTO " + TABLE_NAME + " VALUES ('%s', '%s', %d, %.3f, %d, %d)",
                        UUID.randomUUID(),
                        ((Student) groupInfo).getSurname(),
                        groupInfo.getGroup(),
                        groupInfo.getAverageMark(),
                        ((Student)groupInfo).getMarkMath(),
                        ((Student) groupInfo).getMarkEP()
                );
                System.out.println(sql);
                this.statement.executeUpdate(sql);
            }
        }
    }

    public void close() throws SQLException{
         try {
             if (this.statement != null) {
                 this.statement.close();
             }
         } finally {
             if (this.connection != null) {
                 this.connection.close();
             }
         }
    }
}
