package com.palg.tp.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonMapper implements ObjectMapper {

    private final Gson mapper;

    public JsonMapper() {
        this.mapper = new GsonBuilder()
                .addSerializationExclusionStrategy(new PersistableStrategy())
                .create();
    }


    public String toJson(Object object) {
        return this.mapper.toJson(object);
    }

    public <T> T toObject(String json, Class<T> c) {
        return this.mapper.fromJson(json, c);
    }


}