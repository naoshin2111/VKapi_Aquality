package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ConfigReader {

    private final JsonNode rootNode;

    public ConfigReader(String configFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rootNode = objectMapper.readTree(new File(configFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigValue(String key) {
        return rootNode.path(key).asText();
    }
}
