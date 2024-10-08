package global.goit.edu.feature.prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Prefs {
    public static final String DB_JDBC_CONNECTION_URL = "jdbcUrl";
    public static final String DB_JDBC_CONNECTION_USER_NAME = "jdbcName";
    public static final String DB_JDBC_CONNECTION_PASSWORD = "jdbcPassword";
    public static final String DEFAULT_PREFS_FILE_NAME = "D:\\Java\\IDEProjects\\Developer\\DevClassModule6\\src\\prefs.json";
    public static final String INIT_DB_SQL_FILE_PATH = "initDbFilePath";
    private Map<String, Object> prefs = new HashMap<>();

    public Prefs() {
        this(DEFAULT_PREFS_FILE_NAME);
    }

    public Prefs(String fileName) {
        try {
            String json = Files.readString(Path.of(fileName));

            TypeToken<?> typeToken = TypeToken.getParameterized(
                    Map.class,
                    String.class,
                    Object.class
            );

            prefs = new Gson().fromJson(json, typeToken.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return getPref(key).toString();
    }

    private Object getPref(String key) {
        return prefs.get(key);
    }
}
