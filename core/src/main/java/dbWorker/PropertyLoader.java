package dbWorker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertyLoader {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
        if(propertiesFile == null) {
            throw new NullPointerException("Filen properties '" + PROPERTIES_FILE + "' saknas!");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getProperty(String key) {
        String property = PROPERTIES.getProperty(key);
        if(property == null || property.trim().length() == 0) {
            throw new NullPointerException("Kunde inte ladda properties för '" + key + ".");
        }

        return property;
    }
}
