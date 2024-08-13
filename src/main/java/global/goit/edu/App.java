package global.goit.edu;

import global.goit.edu.Human.HumanService;
import global.goit.edu.feature.DatabaseInitService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Locale;

public class App {

    public static void main(String[] args) {

        Storage storage = Storage.getInstance();

        DatabaseInitService databaseInitService = new DatabaseInitService();

        databaseInitService.initDb(storage);

/*        String insertSql = String.format(
                "INSERT INTO human (name, birthday) VALUES ('%s', '%s');",
                        "Elon Mask",
                LocalDate.now().toString()
        );
        System.out.println(insertSql);

        storage.executeUpdate(insertSql);*/

/*        String selectSql = "SELECT * FROM human where id=1";

        try {
            Statement query = storage.getConnection().createStatement();
            ResultSet rs = query.executeQuery(selectSql);

            if (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                LocalDate birthday = LocalDate.parse(rs.getString("birthday"));

                System.out.println("id = " + id);
                System.out.println("name = " + name);
                System.out.println("birthday = " + birthday);

            } else {
                System.out.println("human with this id not found");
            }

            rs.close();
            storage.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        HumanService humanService = new HumanService(storage);

        humanService.createNewHuman("John Call", LocalDate.now().minusMonths(1));
        //humanService.printHumanIds();
        humanService.printHumanInfo(2);
    }
}
