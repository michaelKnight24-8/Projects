package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection conn = null;
    public DBUtil() { }

    public static Connection getConnection() {
        try {
            if (conn == null)
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\mknig\\Documents\\database\\PatientPortal.db");
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
