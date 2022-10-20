package com.palg.tp.mapper;

import java.util.Optional;

public interface ObjectMapper {
    Optional<String> toJson(Object object);
    <T> T toObject(String json, Class<T> c);
}
