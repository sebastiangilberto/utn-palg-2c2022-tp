package com.palg.tp.persistence;


import com.palg.tp.listener.SessionListener;
import com.palg.tp.session.Session;

import java.util.List;

public class PersistentObjectsImpl implements PersistentObjects {

    private Session session;
    private List<SessionListener> sessionListeners;

    @Override
    public void creteSession(long key, long timeout) {
        if(session == null) {
            session = new Session(key, timeout);
            sessionListeners.forEach(sessionListener -> sessionListener.sessionOpened(key));
        } else throw new RuntimeException("Hay una session creada con la clave %s".formatted(key));
    }

    @Override
    public void store(long key, Object o) {

    }

    @Override
    public Object load(long key, Class<?> clazz) {
        return null;
    }

    @Override
    public Object remove(long key, Class<?> clazz) {
        return null;
    }

    @Override
    public void destroySession(long key) {
        this.session = null;
    }

    @Override
    public void addListener(SessionListener sessionListener) {
        sessionListeners.add(sessionListener);
    }

    @Override
    public void removeListener(SessionListener sessionListener) {
        sessionListeners.remove(sessionListener);
    }
}
