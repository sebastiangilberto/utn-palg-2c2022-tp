package com.palg.tp.manager;

import com.palg.tp.dao.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class SessionChecker extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(SessionChecker.class);
    private final SessionManager manager;

    private final Map<Long, SessionStatus> statuses;

    public SessionChecker(SessionManager manager) {
        this.manager = manager;
        this.statuses = new HashMap<>();
    }

    @Override
    public void run() {
        logger.info("[sessionChecker] running checker, retrieving sessions from DB");
        this.manager.getSessions().forEach(this::checkStatus);
    }


    /*  obtengo el status actual y el anterior
        me fijo si cambio:
        a) cambio -> notifico según el nuevo valor y actualizo valor en mapa
        b) no cambio -> notifico según el valor actual
    */
    public void checkStatus(Session session) {
        logger.info("[sessionChecker] cheking status for session: %d".formatted(session.getKey()));

        SessionStatus currentStatus = getSessionStatus(session);
        SessionStatus lastStatus = this.statuses.get(session.getKey());

        if (lastStatus == null || lastStatus != currentStatus) {
            this.notifyNewStatus(session.getKey(), currentStatus);
            this.statuses.put(session.getKey(), currentStatus);
        } else {
            this.notifySameStatus(session.getKey(), currentStatus);
        }
    }

    private void notifyNewStatus(long key, SessionStatus status) {
        if (status == SessionStatus.OPEN) {
            logger.info("[sessionChecker] session %d is now OPEN, notifying listeners".formatted(key));
            this.manager.getListeners().forEach(l -> l.sessionOpened(key));
        } else {
            logger.info("[sessionChecker] session %d is now CLOSED, notifying listeners".formatted(key));
            this.manager.getListeners().forEach(l -> l.sessionClosed(key));
        }
    }

    private void notifySameStatus(long key, SessionStatus status) {
        if (status == SessionStatus.OPEN) {
            logger.info("[sessionChecker] session %d remains OPEN, notifying listeners".formatted(key));
            this.manager.getListeners().forEach(l -> l.sessionStillOpened(key));
        } else {
            logger.info("[sessionChecker] session %d remains CLOSED, notifying listeners".formatted(key));
            this.manager.getListeners().forEach(l -> l.sessionStillClosed(key));
        }
    }

    private SessionStatus getSessionStatus(Session session) {
        return (System.currentTimeMillis() - session.getLastAccess()) > session.getTimeout() ? SessionStatus.CLOSE : SessionStatus.OPEN;
    }
}