package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection conn = null;
    public DBUtil() { }
    private static String CONNECTION = "jdbc:sqlite:C:\\Users\\mknig\\Documents\\database\\EntertainmentOrganizer.db";

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e);
        }

        return conn;
    }

    public static void close(Connection conn) {
        try { conn.close(); }
        catch (SQLException error) { System.out.println("SQL ERROR: " + error); }
    }
}
