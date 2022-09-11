package com.palg.tp.session;

public class Session {

    private long key;
    private long timeout;
    private SessionStatus status;

    public Session(long key, long timeout) {
        this.key = key;
        this.timeout = timeout;
        this.status = SessionStatus.OPENED;
    }

    public long getKey() {
        return key;
    }

    public long getTimeout() {
        return timeout;
    }

    public void closeSession() {
        this.status = SessionStatus.CLOSED;
    }

    public void openSession() {
        this.status = SessionStatus.OPENED;
    }
}
