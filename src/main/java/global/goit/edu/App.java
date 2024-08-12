package global.goit.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {

        Storage storage = Storage.getInstance();

        Connection connection = storage.getConnection();

    }
}
