package global.goit.edu.feature;

import global.goit.edu.Storage;
import global.goit.edu.feature.prefs.Prefs;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public void initDb (String connectionUrl) {


        try {
            Flyway flyway = Flyway
                    .configure()
/*                    .baselineOnMigrate(true)
                    .baselineVersion("0")*/
                    .dataSource(connectionUrl, null, null)
                    .load();

            flyway.migrate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
