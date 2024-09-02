package global.goit.edu.Human;

import global.goit.edu.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HumanServiceV2 {

    private Connection conn;
    private PreparedStatement insertStatement;
    private PreparedStatement selectByIdStatement;
    private PreparedStatement selectAllStatement;
    private PreparedStatement updateStatement;

    public HumanServiceV2(Storage storage) {
        conn = storage.getConnection();

        try {

            insertStatement = conn.prepareStatement(
                    "INSERT INTO HUMAN (name, birthday) VALUE (?, ?)"
            );

            selectByIdStatement = conn.prepareStatement(
                    "SELECT * FROM human WHERE id = (?)"
            );

            selectAllStatement = conn.prepareStatement(
                    "SELECT id FROM human"
            );

            updateStatement = conn.prepareStatement(
                    "UPDATE human SET name = ? WHERE name = ?"
            );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createNewHumans(String[] names, LocalDate[] birthdays) {
        try {
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                LocalDate birthday = birthdays[i];


                insertStatement.setString(1, name);
                insertStatement.setString(2, birthday.toString());

                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createHuman(String name, LocalDate birthday) {

        try {
            insertStatement.setString(1, name);
            insertStatement.setString(2, birthday.toString());
            return (insertStatement.executeUpdate() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean printHumanInfoById(long id) {

        try {
            selectByIdStatement.setLong(1, id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try (ResultSet rs = selectByIdStatement.executeQuery()) {

            if (!rs.next()) {
                System.out.println("Human with id" + id + " not found");
                return false;
            }

            String name = rs.getString("name");
            String birthday = rs.getString("birthday");

            System.out.println("name = " + name + ", birthday = " + birthday);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Long> getIds() {
        List<Long> result = new ArrayList<>();
        try (ResultSet rs = selectAllStatement.executeQuery()) {
            while (rs.next()) {
                result.add(rs.getLong("id"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void renameHuman(Map<String, String> renameMap) throws SQLException {

        conn.setAutoCommit(false);

        for (Map.Entry<String, String> keyVale : renameMap.entrySet()) {
            updateStatement.setString(1, keyVale.getValue());
            updateStatement.setString(2, keyVale.getKey());

            updateStatement.addBatch();
        }

        try {
            updateStatement.executeBatch();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
        }
        finally {
            conn.setAutoCommit(true);
        }


    }


}