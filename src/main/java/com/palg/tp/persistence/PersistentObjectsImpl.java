package com.palg.tp.persistence;


import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.manager.SessionManager;
import com.palg.tp.listener.SessionListener;
import com.palg.tp.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersistentObjectsImpl implements PersistentObjects {

    private static final Logger logger = LoggerFactory.getLogger(PersistentObjectsImpl.class);
    private final ObjectMapper mapper;
    private final SessionManager manager;

    public PersistentObjectsImpl(ObjectMapper mapper, SessionManager manager) {
        this.mapper = mapper;
        this.manager = manager;
    }

    @Override
    public void store(long key, Object o) {
        logger.info("[persistentObjects] saving object %s for session %d".formatted(o.getClass().getCanonicalName(), key));
        this.mapper.toJson(o)
                .map(json -> new ObjectDetail(key, o.getClass().getCanonicalName(), json))
                .ifPresentOrElse(details -> this.manager.store(key, details), () -> logger.info("[persistentObjects] object is not persistable for session %d".formatted(key)));
    }

    @Override
    public Object load(long key, Class<?> clazz) {
        Optional<ObjectDetail> detail = this.manager.load(key, clazz);

        logger.info("[persistentObjects] loading object from session: %s".formatted(detail));

        return detail.map(d -> this.mapper.toObject(d.getData(), clazz)).orElse(null);
    }

    @Override
    public Object remove(long key, Class<?> clazz) {
        logger.info("[persistentObjects] removing object %s from session %d".formatted(clazz, key));

        Optional<ObjectDetail> detail = this.manager.remove(key, clazz);

        return detail.map(d -> this.mapper.toObject(d.getData(), clazz)).orElse(null);
    }

    @Override
    public void createSession(long key, long timeout) {
        this.manager.createSession(key, timeout);
    }

    @Override
    public void destroySession(long key) {
        this.manager.destroySession(key);
    }

    @Override
    public void addListener(SessionListener sessionListener) {
        this.manager.addListener(sessionListener);
    }

    @Override
    public void removeListener(SessionListener sessionListener) {
        this.manager.removeListener(sessionListener);
    }
}
