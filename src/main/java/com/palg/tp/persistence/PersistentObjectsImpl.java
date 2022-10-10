package com.palg.tp.persistence;


import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.repository.ObjectDetailRepository;
import com.palg.tp.dao.ObjectId;
import com.palg.tp.listener.SessionListener;
import com.palg.tp.mapper.ObjectMapper;
import com.palg.tp.session.Session;
import org.apache.logging.log4j.util.Strings;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersistentObjectsImpl implements PersistentObjects {

    private final List<Session> sessions;
    //private final List<SessionListener> sessionListeners;

    private final ObjectMapper mapper;

    private final ObjectDetailRepository repository;

    public PersistentObjectsImpl(ObjectMapper mapper, ObjectDetailRepository repository){
        this.mapper = mapper;
        this.repository = repository;
        this.sessions = new ArrayList<>();
    }

    @Override
    public void creteSession(long key, long timeout) {
        this.sessions.add(new Session(key, timeout));

        /** TODO: que hacemos si ya existe una session con esa key?
        if(session == null) {
            session = new Session(key, timeout);
            sessionListeners.forEach(sessionListener -> sessionListener.sessionOpened(key));
        } else throw new RuntimeException("Hay una session creada con la clave %s".formatted(key));
         **/
    }

    @Override
    public void store(long key, Object o) {
        // TODO: chequear que exista la session
        String json = this.mapper.toJson(o);
        ObjectDetail details = new ObjectDetail(key, o.getClass().getCanonicalName(), json);
        // TODO: que hacemos si ya existe un registro con esa key?
        this.repository.save(details);
    }

    @Override
    public Object load(long key, Class<?> clazz) {
        // TODO: chequear que exista la session
        Optional<ObjectDetail> details = this.repository.findById(new ObjectId(key, clazz.getCanonicalName()));
        return this.mapper.toObject(details.map(ObjectDetail::getData).orElse(Strings.EMPTY), clazz);
    }

    @Transactional
    @Override
    public Object remove(long key, Class<?> clazz) {
        ObjectId id = new ObjectId(key, clazz.getCanonicalName());
        ObjectDetail details = this.repository.findById(id).orElse(null);

        if (details != null) {
            this.repository.deleteById(id);
            return details;
        }

        return null;
    }

    @Override
    public void destroySession(long key) {
        // TODO: que hacemos con los registros de la base asociados a la session?
        this.sessions.removeIf(session -> key == session.getKey());
    }

    @Override
    public void addListener(SessionListener sessionListener) {
        //sessionListeners.add(sessionListener);
    }

    @Override
    public void removeListener(SessionListener sessionListener) {
       // sessionListeners.remove(sessionListener);
    }
}
