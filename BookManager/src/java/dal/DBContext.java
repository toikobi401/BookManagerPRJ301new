
package dal;
import java.sql.*;
import java.util.ArrayList;


public abstract class DBContext<T> {
    protected Connection connection;
    public DBContext() {
    try {
        String user = "dat";
        String pass = "123";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;encrypt=true;trustServerCertificate=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("JDBC Driver loaded successfully!");
        connection = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected successfully!");
    } catch (ClassNotFoundException ex) {
        throw new RuntimeException("JDBC Driver not found: " + ex.getMessage(), ex);
    } catch (SQLException ex) {
        throw new RuntimeException("Database connection failed: " + ex.getMessage(), ex);
    }
}
    
    public abstract ArrayList<T> list();
    public abstract T get(int id);
    public abstract void insert(T model);
    public abstract void update(T model);
    public abstract void delete(T model);
}
