package config;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUserConfig {

    private static final ISettingsFile SETTINGS_FILE = new JsonSettingsFile("testUser.json");
    public static String getLogin() {
        return SETTINGS_FILE.getValue("/login").toString();
    }

    public static String getPassword() {
        return SETTINGS_FILE.getValue("/password").toString();
    }

    public static String getToken() {
        return SETTINGS_FILE.getValue("/token").toString();
    }
}
