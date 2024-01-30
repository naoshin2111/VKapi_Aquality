package config;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataConfig {

    private static final ISettingsFile SETTINGS_FILE = new JsonSettingsFile("testData.json");

    public static String getImagePath() {
        return SETTINGS_FILE.getValue("/imagePath").toString();
    }
}
