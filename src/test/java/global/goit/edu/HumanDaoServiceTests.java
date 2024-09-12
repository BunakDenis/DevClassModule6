package global.goit.edu;

import global.goit.edu.Human.Human;
import global.goit.edu.Human.HumanDaoService;
import global.goit.edu.feature.DatabaseInitService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HumanDaoServiceTests {
    private static HumanDaoService humanDaoService;
    private static Connection connection;

    @BeforeEach
    public void BeforeEach() throws SQLException {
        String connectionUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        DatabaseInitService dbInit = new DatabaseInitService();
        dbInit.initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        humanDaoService = new HumanDaoService(connection);
        humanDaoService.clear();
    }

    @AfterEach
    public void AfterEach() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThatHumanCreatedCorrectly() throws SQLException {

        //Given
        List<Human> originalHumans = new ArrayList<>();
        Human originalHuman = new Human();
        Human humanWithBirthdayNull = new Human();
        Human humanWithGenderNull = new Human();

        //Original
        originalHuman.setName("Denis");
        originalHuman.setBirthday(LocalDate.now());
        originalHuman.setGender(Human.Gender.male);

        //Human with birthday null
        humanWithBirthdayNull.setName("TestWithBirthdayNull");
        humanWithBirthdayNull.setBirthday(null);
        humanWithBirthdayNull.setGender(Human.Gender.male);

        //Human with Gender null
        humanWithGenderNull.setName("TestWithBGenderNull");
        humanWithGenderNull.setBirthday(LocalDate.now());
        humanWithGenderNull.setGender(null);

        originalHumans.add(originalHuman);
        originalHumans.add(humanWithBirthdayNull);
        originalHumans.add(humanWithGenderNull);

        for (Human original : originalHumans) {

            long id = humanDaoService.createHuman(original);

            Human saved = humanDaoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getName(), saved.getName());
            Assertions.assertEquals(original.getBirthday(), saved.getBirthday());
            Assertions.assertEquals(original.getGender(), saved.getGender());
        }
    }

    @Test
    public void testThatMethodGetAllWorkOk() throws SQLException {

        //Given
        humanDaoService.clear();
        Human expected = new Human();
        expected.setName("Test");
        expected.setBirthday(LocalDate.now());
        expected.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(expected);
        expected.setId(id);

        List<Human> expectedHumans = Collections.singletonList(expected);
        List<Human> actualHumans = humanDaoService.getAll();

        //Then
        Assertions.assertEquals(expectedHumans, actualHumans);
    }

    @Test
    public void testMethodUpdate() throws SQLException {

        //Given
        Human original = new Human();
        original.setName("Test");
        original.setBirthday(LocalDate.now());
        original.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(original);
        original.setId(id);
        original.setName("New Name");
        original.setBirthday(LocalDate.now().minusYears(1));
        original.setGender(Human.Gender.female);
        humanDaoService.update(original);
        Human updated = humanDaoService.getById(id);

        //Then
        Assertions.assertEquals(id, updated.getId());
        Assertions.assertEquals("New Name", updated.getName());
        Assertions.assertEquals(LocalDate.now().minusYears(1), updated.getBirthday());
        Assertions.assertEquals(Human.Gender.female, updated.getGender());
    }

    @Test
    public void testThatMethodDeleteWorkOk() throws SQLException {

        //Given
        Human original = new Human();
        original.setName("Test");
        original.setBirthday(LocalDate.now());
        original.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(original);
        humanDaoService.deleteById(id);

        //Then
        Assertions.assertNull(humanDaoService.getById(id));
    }

    @Test
    public void testThatMethodExistsWorkOk() throws SQLException {

        //Given
        Human human = new Human();

        human.setName("Test");
        human.setBirthday(LocalDate.now());
        human.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(human);
        boolean actual = humanDaoService.exists(id);

        //Then
        Assertions.assertEquals(true, actual);
    }

    @Test
    public void testSaveOnNewUser() throws SQLException {

        //Given
        Human human = new Human();
        human.setName("Test");
        human.setBirthday(LocalDate.now());
        human.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(human);

        //Then
        Assertions.assertTrue(humanDaoService.exists(id));
    }

    @Test
    public void testSaveOnExistingUser() throws SQLException {

        //Given
        Human human = new Human();
        human.setName("Test");
        human.setBirthday(LocalDate.now());
        human.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(human);

        human.setId(id);
        human.setName("NewName");
        humanDaoService.save(human);
        Human updated = humanDaoService.getById(human.getId());

        //Then
        Assertions.assertEquals(human.getId(), updated.getId());
    }

    @Test
    public void testOnEmptyDatabase() throws SQLException {

        Assertions.assertEquals(
                Collections.emptyList(),
                humanDaoService.searchByName("name")
        );

    }

    @Test
    public void testSearchOnFillDatabase() throws SQLException {

        //Given
        Human human = new Human();
        human.setName("Test");
        human.setBirthday(LocalDate.now());
        human.setGender(Human.Gender.male);

        //When
        long id = humanDaoService.createHuman(human);
        List<Human> actual = humanDaoService.searchByName("Test");

        //Then
        Assertions.assertEquals(
                1, actual.size()
        );
        Assertions.assertEquals(
                id, actual.get(0).getId()
        );
    }
}