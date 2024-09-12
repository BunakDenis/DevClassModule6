package global.goit.edu.Human;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HumanDaoService {

    private PreparedStatement createHumanStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement selectMaxIdStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement updatedStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement existByIdStatement;
    private PreparedStatement clearStatement;
    private PreparedStatement searchStatement;

    public HumanDaoService(Connection connection) throws SQLException {
        createHumanStatement = connection.prepareStatement(
                "INSERT INTO human (name, birthday, gender) VALUES (?, ?, ?)"
        );

        getByIdStatement = connection.prepareStatement(
                "SELECT id, name, birthday, gender FROM human WHERE id = ?"
        );

        selectMaxIdStatement = connection.prepareStatement(
                "SELECT MAX(id) as MaxId FROM human"
        );

        getAllStatement = connection.prepareStatement(
                "SELECT * FROM human"
        );

        updatedStatement = connection.prepareStatement(
                "UPDATE human SET name = ?, birthday = ?, gender = ? WHERE id = ?"
        );

        deleteByIdStatement = connection.prepareStatement(
                "DELETE FROM human WHERE id = ?"
        );

        existByIdStatement = connection.prepareStatement(
                "SELECT count(*) > 0 AS HumanExists FROM human WHERE id = ?"
        );

        clearStatement = connection.prepareStatement(
                "DELETE FROM human"
        );

        searchStatement = connection.prepareStatement(
                "SELECT id, name, birthday, gender FROM human WHERE name LIKE ?"
        );

    }

    public long createHuman(Human human) throws SQLException {
        long result = -1;

        createHumanStatement.setString(1, human.getName());
        createHumanStatement.setString(2,
                human.getBirthday() == null ? null : human.getBirthday().toString());
        createHumanStatement.setString(3,
                human.getGender() == null ? null : human.getGender().toString());

        createHumanStatement.executeUpdate();

        ResultSet rs = selectMaxIdStatement.executeQuery();

        while (rs.next()) {
            result = rs.getLong("MaxId");
        }

        return result;
    }

    public Human getById(long id) throws SQLException {

        Human human = new Human();

        getByIdStatement.setLong(1, id);

        try (ResultSet rs = getByIdStatement.executeQuery()) {

            if (rs.next()) {
                human.setId(rs.getLong("id"));
                human.setName(rs.getString("name"));

                String birthday = rs.getString("birthday");
                if (birthday != null) {
                    human.setBirthday(LocalDate.parse(rs.getString("birthday")));
                }

                Human.Gender gender = Human.Gender.valueOf(rs.getString("gender"));
                if (gender != null) {
                    human.setGender(gender);
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return human;
    }

    public List<Human> getAll() {

        List<Human> result = new ArrayList<>();

        getHuman(result);
        return result;
    }

    public void update(Human human) throws SQLException {

        updatedStatement.setString(1, human.getName());
        updatedStatement.setString(2, human.getBirthday().toString());
        updatedStatement.setString(3, human.getGender().name());
        updatedStatement.setLong(4, human.getId());

        updatedStatement.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {

        deleteByIdStatement.setLong(1, id);
        deleteByIdStatement.executeUpdate();

    }

    public boolean exists(long id) throws SQLException {

        existByIdStatement.setLong(1, id);

        try(ResultSet rs = existByIdStatement.executeQuery()) {

            rs.next();

            return rs.getBoolean("HumanExists");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public long save(Human human) throws SQLException {

        if (exists(human.getId())) {
            update(human);
            return human.getId();
        } else {
            return createHuman(human);
        }
    }

    public void clear() throws SQLException {
        clearStatement.executeUpdate();
    }

    public List<Human> searchByName(String query) throws SQLException {
        List<Human> result = new ArrayList<>();

        searchStatement.setString(1, "%" + query + "%");
        getHuman(result);

        return result;
    }

    private void getHuman(List<Human> result) {
        try (ResultSet rs = getAllStatement.executeQuery()) {

            while (rs.next()) {
                Human human = new Human();

                human.setId(rs.getLong("id"));
                human.setName(rs.getString("name"));
                human.setBirthday(LocalDate.parse(rs.getString("birthday")));
                human.setGender(Human.Gender.valueOf(rs.getString("gender")));
                result.add(human);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}