package global.goit.edu.feature.prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Prefs {
    public static final String DB_JDBS_CONNECTION_URL = "jdbsUrl";
    public static final String DB_JDBS_CONNECTION_USER_NAME = "jdbsName";
    public static final String DB_JDBS_CONNECTION_PASSWORD = "jdbsPassword";
    public static final String DEFAULT_PREFS_FILE_NAME = "D:\\Java\\IDEProjects\\Developer\\DevClassModule6\\src\\prefs.json";
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

    public Object getPref(String key) {
        return prefs.get(key);
    }
}
