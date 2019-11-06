package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileLoader {

    private static Logger log = LoggerFactory.getLogger(JsonFileLoader.class);

    public static String readJsonFileAsString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            String msg = "Error reading json file: " + filePath;
            log.error(msg, e);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
