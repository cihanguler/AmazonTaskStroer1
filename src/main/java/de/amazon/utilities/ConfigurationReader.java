package de.amazon.utilities;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

    static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream input= ConfigurationReader.class.getClassLoader().getResourceAsStream("configuration.properties");
            properties.load(input);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param keyName
     * @return
     */
    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }

}
