package config;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class ApiUrlConfig {

    private static final ISettingsFile SETTINGS_FILE = new JsonSettingsFile("apiUri.json");

    public static String getApiUrl() {
        return SETTINGS_FILE.getValue("/apiUrl").toString();
    }
}
