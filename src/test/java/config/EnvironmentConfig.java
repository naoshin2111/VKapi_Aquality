package config;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnvironmentConfig {

    private static final ISettingsFile SETTINGS_FILE = new JsonSettingsFile("environment.json");

    public static String getUrl() {
        return SETTINGS_FILE.getValue("/url").toString();
    }

    public static String getApiUrl() {
        return SETTINGS_FILE.getValue("/apiUrl").toString();
    }

    public static String getApiVersion() {
        return SETTINGS_FILE.getValue("/vkApiVersion").toString();
    }
}
