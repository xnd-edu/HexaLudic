package xndr.hexaludic.hexaludic.common;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class Config {
    private Properties properties;

    public Config() {
        properties = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/xndr/hexaludic/hexaludic/config.properties")) {
            if (in == null) {
                log.error("No se encontró config.properties");
            } else {
                properties.load(in);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String loadPathProperties() {
        return properties.getProperty("pathJson");
    }

    public String loadPasswordProperties() {
        return properties.getProperty("adminPass");
    }
}


