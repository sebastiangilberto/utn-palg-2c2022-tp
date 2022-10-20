package com.palg.tp.dao;

import javax.persistence.*;

@Entity
@IdClass(ObjectId.class)
public class ObjectDetail {

    @Id
    private String objectType;

    @Id
    private long key;

    private String data;

    public ObjectDetail(long key, String objectType, String data) {
        this.key = key;
        this.objectType = objectType;
        this.data = data;
    }

    public ObjectDetail() {
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ObjectDetail{" +
                "objectType='" + objectType + '\'' +
                ", key=" + key +
                ", data='" + data + '\'' +
                '}';
    }
}
