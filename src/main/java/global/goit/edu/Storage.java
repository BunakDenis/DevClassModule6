package global.goit.edu;

import global.goit.edu.feature.prefs.Prefs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Storage {

    private static final Storage INSTANCE = new Storage();
    private Connection connection;

    private Storage() {

        try {

            Prefs prefs = new Prefs();

            connection = DriverManager.getConnection(prefs.getString(Prefs.DB_JDBC_CONNECTION_URL),
                    prefs.getString(Prefs.DB_JDBC_CONNECTION_USER_NAME),
                    prefs.getString(Prefs.DB_JDBC_CONNECTION_PASSWORD));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String sql) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Storage getInstance() {
        return INSTANCE;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}