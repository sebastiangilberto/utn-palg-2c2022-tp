package com.palg.tp.persistence;

import com.palg.tp.listener.SessionListener;

public interface PersistentObjects {

    /**
     * Crea una sesion con identificada por una key y con un timeout especificado.
     *
     * @param key
     * @param timeout
     */
    void creteSession(long key, long timeout);

    /**
     * Almacena un objeto asociado a dicha clave
     *
     * @param key
     * @param o
     */
    void store(long key, Object o);

    /**
     * Retorna la instancia de la clase clazz
     *
     * @param key
     * @param clazz
     * @return
     */
    Object load(long key, Class<?> clazz);

    /**
     * Remueve la instancia del objeto de la clase clazz asociado a la sesión
     *
     * @param key
     * @param clazz
     * @return
     */
    Object remove(long key, Class<?> clazz);

    /**
     * Destruye la sesión identificada por key
     *
     * @param key
     */
    void destroySession(long key);

    /**
     * Registra un listener de sesión
     *
     * @param lst
     */
    void addListener(SessionListener lst);

    /**
     * Remueve un listener previamente registrado
     *
     * @param lst
     */
    void removeListener(SessionListener lst);

}
