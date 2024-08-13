package global.goit.edu.feature;

import global.goit.edu.Storage;
import global.goit.edu.feature.prefs.Prefs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseInitService {

    public void initDb (Storage storage) {

        try {
            String initDbFileName = new Prefs().getString(Prefs.INIT_DB_SQL_FILE_PATH);
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Path.of(initDbFileName))
            );
            storage.executeUpdate(sql);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
