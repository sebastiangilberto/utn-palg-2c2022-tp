package com.palg.tp.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ObjectId.class)
public class ObjectDetail {

    @Id
    private long sessionKey;

    @Id
    private String objectType;

    private String data;

    public ObjectDetail(long sessionKey, String objectType, String data) {
        this.sessionKey = sessionKey;
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
                "sessionKey=" + sessionKey +
                ", objectType='" + objectType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
