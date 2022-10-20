package com.palg.tp.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JsonMapper implements ObjectMapper {

    private final Gson mapper;
    private static final String EMPTY_JSON = "{}";

    public JsonMapper() {
        this.mapper = new GsonBuilder()
                .addSerializationExclusionStrategy(new PersistableStrategy())
                .create();
    }


    public Optional<String> toJson(Object object) {
        String s = this.mapper.toJson(object);

        if (EMPTY_JSON.equals(s)) {
            return Optional.empty();
        }

        return Optional.of(s);
    }

    public <T> T toObject(String json, Class<T> c) {
        return this.mapper.fromJson(json, c);
    }


}