package global.goit.edu.feature;

import global.goit.edu.Storage;
import global.goit.edu.feature.prefs.Prefs;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public void initDb (Storage storage) {

        String connectionUrl = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);
        String dbUserName = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_USER_NAME);
        String dbPassword = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_PASSWORD);
        try {
            Flyway flyway = Flyway
                    .configure()
                    .baselineOnMigrate(true)
                    .baselineVersion("0")
                    .dataSource(connectionUrl, dbUserName, dbPassword)
                    .load();

            flyway.migrate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
