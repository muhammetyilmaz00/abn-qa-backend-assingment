package gitLabIssues.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFactory {

    /**
     * Reads the file "application.properties" and loads its content into the configFile variable
     */
    private static Properties configFile;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config/application.properties");
            configFile = new Properties();
            configFile.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Could not load properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * retrieves a property with the specified key from the configFile object and returns its corresponding value as a string
     */
    public static String getProperty(String text) {
        return configFile.getProperty(text);
    }
}
