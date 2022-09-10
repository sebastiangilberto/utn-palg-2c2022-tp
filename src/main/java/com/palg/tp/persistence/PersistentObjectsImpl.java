package com.palg.tp.persistence;


import com.palg.tp.listener.SessionListener;

public class PersistentObjectsImpl implements PersistentObjects {
    @Override
    public void creteSession(long key, long timeout) {

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

    }

    @Override
    public void addListener(SessionListener lst) {

    }

    @Override
    public void removeListener(SessionListener lst) {

    }
}
