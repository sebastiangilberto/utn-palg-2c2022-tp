package com.palg.tp.mapper;

public interface ObjectMapper {
    String toJson(Object object);
    <T> T toObject(String json, Class<T> c);
}
