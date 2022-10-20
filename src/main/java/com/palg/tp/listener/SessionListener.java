package com.palg.tp.listener;

public interface SessionListener {

    /**
     * Se invoca luego de cerrar una sesión
     *
     * @param key
     */
    void sessionClosed(long key);

    /**
     * Se invoca cada x periodo de tiempo, indicando que la sesión está cerrada
     *
     * @param key
     */
    void sessionStillClosed(long key);

    /**
     * Se invoca luego de abrir una sesión
     *
     * @param key
     */
    void sessionOpened(long key);

    /**
     * Se invoca cada x periodo de tiempo, indicando que la sesión está abierta
     *
     * @param key
     */
    void sessionStillOpened(long key);

}