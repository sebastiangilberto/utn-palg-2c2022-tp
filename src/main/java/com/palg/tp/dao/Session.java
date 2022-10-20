package com.palg.tp.dao;

import javax.persistence.*;

@Entity
public class Session {

    @Id
    private long key;

    private long timeout;

    private long lastAccess;

    public Session() {

    }

    public Session(long key, long timeout) {
        this.key = key;
        this.timeout = timeout;
        this.lastAccess = System.currentTimeMillis();
    }

    public long getLastAccess() {
        return this.lastAccess;
    }

    public long getKey() {
        return this.key;
    }

    public void setLastAccess(long dateInMillis) {
        this.lastAccess = dateInMillis;
    }

    @Override
    public String toString() {
        return "Session{" +
                "key=" + key +
                ", timeout=" + timeout +
                ", lastAccess=" + lastAccess +
                '}';
    }

    public long getTimeout() {
        return this.timeout;
    }
}

