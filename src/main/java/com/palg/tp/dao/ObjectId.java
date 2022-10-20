package com.palg.tp.dao;

import java.io.Serializable;
import java.util.Objects;

public class ObjectId implements Serializable {
    private long key;

    private String objectType;

    public ObjectId(){}

    public ObjectId(long key, String objectType) {
        this.key = key;
        this.objectType = objectType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectId objectId = (ObjectId) o;
        return Objects.equals(key, objectId.key) && Objects.equals(objectType, objectId.objectType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, objectType);
    }
}
