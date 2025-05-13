package xndr.hexaludic.hexaludic.common;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class Config {

    public String loadPathProperties(){
        Properties properties= new Properties();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(input);
            return (String)properties.get("pathJson");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        }
        return null;//path no encontrado
    }
}
