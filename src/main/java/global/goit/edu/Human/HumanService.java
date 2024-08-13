package global.goit.edu.Human;

import global.goit.edu.Storage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class HumanService {
    private Storage storage;

    public HumanService(Storage storage) {
        this.storage = storage;
    }

    public void printHumanIds() {

        try (Statement st = storage.getConnection().createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM human");

            while (rs.next()) {
                long id = rs.getLong("id");
                System.out.println("id = " + id);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createNewHuman(String name, LocalDate birthday) {
        String insertSql = String.format(
                "INSERT INTO human (name, birthday) VALUES ('%s', '%s')",
                name,
                birthday.toString()
        );

        storage.executeUpdate(insertSql);

    }

    public void printHumanInfo(long id) {
        try (Statement st = storage.getConnection().createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM human WHERE id=" + id);

            if (rs.next()) {
                String name = rs.getString("name");
                String birthday = rs.getString("birthday");

                System.out.println("Name: " + name + ", birthday: " + birthday);

            } else {
                System.out.println("Human with id" + id + "not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
