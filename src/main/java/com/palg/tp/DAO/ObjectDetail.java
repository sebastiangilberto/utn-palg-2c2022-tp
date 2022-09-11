package com.palg.tp.DAO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ObjectDetail {
    @Id
    private long key;
    private String json;

    protected ObjectDetail() {

    }

    public ObjectDetail(long key, String json) {
        this.key = key;
        this.json = json;
    }

    @Override
    public String toString() {
        return String.format(
                "Class[key=%2d, json='%s']",
                key, json);
    }
}
