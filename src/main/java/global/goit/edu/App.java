package global.goit.edu;

import global.goit.edu.Human.Human;
import global.goit.edu.Human.HumanGenerate;
import global.goit.edu.Human.HumanService;
import global.goit.edu.Human.HumanServiceV2;
import global.goit.edu.feature.DatabaseInitService;
import global.goit.edu.feature.prefs.Prefs;

import java.time.LocalDate;
import java.util.Locale;

public class App {

    public static void main(String[] args) {

        DatabaseInitService dbInitServ = new DatabaseInitService();
        Storage storage = Storage.getInstance();

        dbInitServ.initDb(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));


        String insertSql = String.format(
                "INSERT INTO human (name, birthday) VALUES ('%s', '%s');",
                        "Elon Mask",
                LocalDate.now().toString()
        );
        System.out.println(insertSql);

        storage.executeUpdate(insertSql);

        Human human = new Human();

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

/*        HumanService humanService = new HumanService(storage);

        humanService.createNewHuman("John Call", LocalDate.now().minusMonths(1));
        //humanService.printHumanIds();
        humanService.printHumanInfo(2);*/

        HumanServiceV2 humanServiceV2 = new HumanServiceV2(storage);

 /*       StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            builder.append("Text");
        }

        boolean result = humanServiceV2.createHuman(builder.toString(),
                LocalDate.now().minusYears(40));

        System.out.println("result = " + result);*/

        //System.out.println(humanServiceV2.printHumanInfoById(1));

/*        List<Long> ids = humanServiceV2.getIds();
        System.out.println(ids.size());*/
        //ids.forEach(humanServiceV2::printHumanInfoById);


/*        List<Long> time = new ArrayList<>();
        for (int i = 0; i < 3; i++) {


            long start = new Date().getTime();

            //10 записей за 36ms
            //100 записей за 235ms
            //1000 записей за 1800ms
            //1000 записей за 14000ms
            //10000 записей за 151782ms
            for (int j = 0; j < 100000; j++) {
                humanServiceV2.createHuman(names[j], birthdays[j]);
            }


            //10 записей за 30ms
            //100 записей за 230ms
            //1000 записей за 1800ms
            //10000 записей за 14000ms
            //100000 записей за 138210ms

            humanServiceV2.createNewHumans(
                    Arrays.copyOf(names, 100000),
                    Arrays.copyOf(birthdays, 100000)
            );

            long finish = (new Date().getTime()) - start;
            time.add(finish);
        }
        int middle = 0;
        for (Long aLong : time) {
            middle += aLong;
        }
        System.out.println(middle / 3);*/

/*        humanServiceV2.createHuman("John", LocalDate.now());
        humanServiceV2.createHuman("Jeniffer", LocalDate.now());
        List<Long> ids = humanServiceV2.getIds();
        System.out.println(ids.size());
        ids.forEach(humanServiceV2::printHumanInfoById);

        Map<String, String> renameMap = new HashMap<>();

        renameMap.put("John", "Ivan");
        renameMap.put("Jeniffer", "Olha");

        try {
            humanServiceV2.renameHuman(renameMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Long> idsAfterUpdate = humanServiceV2.getIds();
        System.out.println(idsAfterUpdate.size());
        idsAfterUpdate.forEach(humanServiceV2::printHumanInfoById);*/
    }
}
