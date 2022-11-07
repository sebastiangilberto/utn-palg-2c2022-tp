package com.palg.tp.manager;

import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.dao.ObjectId;
import com.palg.tp.dao.Session;
import com.palg.tp.exception.SessionNotExistsException;
import com.palg.tp.listener.SessionListener;
import com.palg.tp.repository.ObjectDetailRepository;
import com.palg.tp.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

@Service
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private final List<SessionListener> listeners;

    private final SessionRepository sessionRepository;
    private final ObjectDetailRepository objectDetailRepository;

    public SessionManager(SessionRepository sessionRepository, ObjectDetailRepository objectDetailRepository, @Value("${palg.listener.period}") Long listenerPeriod) {
        this.sessionRepository = sessionRepository;
        this.objectDetailRepository = objectDetailRepository;
        this.listeners = new ArrayList<>();
        Timer timer = new Timer();
        SessionChecker checker = new SessionChecker(this);
        timer.schedule(checker, 0, listenerPeriod);
    }

    @Transactional
    public void createSession(long key, long timeout) {
        this.sessionRepository.findById(key).ifPresentOrElse(
                (session) -> {
                    throw new SessionNotExistsException("session %d already exists".formatted(key));
                },
                () -> {
                    Session session = new Session(key, timeout);
                    logger.info("[manager] creating session %s".formatted(session.toString()));
                    this.sessionRepository.save(session);
                }
        );
    }

    @Transactional
    public void destroySession(long key) {
        this.sessionRepository.findById(key).ifPresentOrElse(
                (session) -> {
                    this.objectDetailRepository.deleteByKey(key);
                    logger.info("[manager] all objects deleted for session %d".formatted(key));
                    this.sessionRepository.deleteById(key);
                    logger.info("[manager] session deleted with key: %d".formatted(key));
                },
                () -> {
                    throw new SessionNotExistsException("session %d not found".formatted(key));
                }
        );
    }

    @Transactional
    public void store(long key, ObjectDetail details) throws SessionNotExistsException {
        this.sessionRepository.findById(key).ifPresentOrElse(
                (session) -> {
                    logger.info("[manager] saving object %s to session %d".formatted(details.toString(), key));
                    this.objectDetailRepository.save(details);
                    this.updateLastAccess(session);
                },
                () -> {
                    throw new SessionNotExistsException("session %d not found".formatted(key));
                }
        );

    }

    @Transactional
    public Optional<ObjectDetail> load(long key, Class<?> clazz) throws SessionNotExistsException {
        return this.sessionRepository.findById(key).map(
                (session) -> {
                    logger.info("[manager] loading object %s from session %d".formatted(clazz.getCanonicalName(), key));
                    this.updateLastAccess(session);
                    return this.objectDetailRepository.findById(new ObjectId(key, clazz.getCanonicalName()));
                }
        ).orElseThrow(
                () -> new SessionNotExistsException("session %d not found".formatted(key))
        );
    }

    @Transactional
    public Optional<ObjectDetail> remove(long key, Class<?> clazz) throws SessionNotExistsException {
        return this.sessionRepository.findById(key).map(
                (session) -> {
                    ObjectId id = new ObjectId(key, clazz.getCanonicalName());
                    return this.objectDetailRepository.findById(id).map(
                            (details) -> {
                                logger.info("[manager] removing from session %d, object %s".formatted(key, details.toString()));
                                this.objectDetailRepository.delete(details);
                                this.updateLastAccess(session);
                                return details;
                            });
                }
        ).orElseThrow(
                () -> new SessionNotExistsException("session %d not found".formatted(key))
        );
    }

    public List<SessionListener> getListeners() {
        return this.listeners;
    }

    Iterable<Session> getSessions() {
        return this.sessionRepository.findAll();
    }

    public void addListener(SessionListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(SessionListener listener) {
        this.listeners.remove(listener);
    }

    private void updateLastAccess(Session session) {
        session.setLastAccess(System.currentTimeMillis());
        logger.info("[manager] updating session %s".formatted(session.toString()));
        this.sessionRepository.save(session);
    }

    public void printEverything() {
        logger.info("==============================================================================================");
        this.sessionRepository.findAll().forEach(s -> logger.info("[manager] session: %s".formatted(s.toString())));
        this.objectDetailRepository.findAll().forEach(o -> logger.info("[manager] object: %s".formatted(o.toString())));
        logger.info("==============================================================================================");
    }
}
