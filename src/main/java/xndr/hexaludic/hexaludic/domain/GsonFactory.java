package xndr.hexaludic.hexaludic.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GsonFactory {
    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .setPrettyPrinting()
                .create();
    }
}
