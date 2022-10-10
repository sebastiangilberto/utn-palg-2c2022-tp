package com.palg.tp;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Temporal {
    public static void main(String... args){
        /**
         PruebaObjects po = new PruebaObjects();
         po.status(1);
         po.addListener(new PruebaListener());
         po.status(1);
         po.createSession(1, 6000L);
         po.status(1);

         po.store(1, "hola mundo");
         po.status(1);
         System.out.println("value from session 1: " + po.load(1));
         po.status(1);
         Thread.sleep(10000L);
         po.status(1);
         po.store(1, "a la monita le gusta el cynar con pomelo");
         po.status(1);
         System.out.println("value from session 1: " + po.load(1));
         Thread.sleep(7000L);
         po.status(1);
         **/
    }
}


class PruebaSession {
    private int key;
    private long ttl;
    private final long timeout;

    public PruebaSession(int key, long timeout) {
        this.ttl = System.currentTimeMillis() + timeout;
        this.timeout = timeout;
        this.key = key;
    }

    public void update() {
        this.ttl = System.currentTimeMillis() + this.timeout;
    }

    public int getKey() {
        return this.key;
    }

    public String getStatus() {
        long now = System.currentTimeMillis();
        return this.ttl < now ? "CLOSED" : "OPEN";
    }
}

class SessionEventManager {

    Map<Integer, String> cache = new HashMap<>();
    Map<Integer, PruebaSession> sessions = new HashMap<>();
    PruebaListener listener;

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void addListener(PruebaListener lst) {
        this.sessions.values().forEach(lst::addSession);
        this.listener = lst;
        this.scheduler.scheduleAtFixedRate(lst, 1, 1, TimeUnit.SECONDS);
        this.listener.cancel();
    }

    public void removeListener(PruebaListener lst) {
        this.listener = null;
    }

    public void sessionCreated(PruebaSession p) {
        this.sessions.putIfAbsent(p.getKey(), p);
        this.listener.addSession(p);
        this.update(p.getKey());
    }

    void update(int key) {
        PruebaSession session = this.sessions.get(key);
        session.update();
        String status = session.getStatus();
        String lastStatus = this.cache.get(session.getKey());

        /**
         if (this.listener != null) {
         if (status.equals("OPEN")) {
         if (status.equals(lastStatus)) {
         this.listener.sessionStillOpen(session.getKey());
         } else {
         this.listener.sessionOpen(session.getKey());
         }
         } else {
         if (status.equals(lastStatus)) {
         this.listener.sessionStillClosed(session.getKey());
         } else {
         this.listener.sessionClosed(session.getKey());
         }
         }
         }
         **/

        if (this.listener != null) {
            if (status.equals("OPEN")) {
                this.listener.sessionOpen(session.getKey());
            } else {
                this.listener.sessionClosed(session.getKey());
            }
        }
        this.cache.putIfAbsent(session.getKey(), status);
    }

    public void printSessionStatus(int key) {
        PruebaSession session = this.sessions.get(key);
        String status;
        if (session == null) {
            status = "NULL";
        } else {
            status = session.getStatus();
        }
        System.out.println("[eventManager] session " + key + " status: " + status);
    }
}

class PruebaListener extends TimerTask {
    private final List<PruebaSession> sessions;

    public void sessionOpen(int key) {
        System.out.println("[listener] session open: " + key);
    }

    public void sessionClosed(int key) {
        System.out.println("[listener] session closed: " + key);
    }

    public void sessionStillOpen(int key) {
        System.out.println("[listener] session still open: " + key);
    }

    public void sessionStillClosed(int key) {
        System.out.println("[listener] session still closed: " + key);
    }

    public void addSession(PruebaSession s) {
        System.out.printf("[listenerTask] thread: %s, session added: %d\n", Thread.currentThread().getName(), s.getKey());
        this.sessions.add(s);
    }

    public PruebaListener() {
        this.sessions = new ArrayList<>();
    }

    @Override
    public void run() {
        this.sessions.forEach(this::process);
    }

    private void process(PruebaSession session) {
        String status = "OPEN".equals(session.getStatus()) ? "STILL OPEN" : "STILL CLOSED";
        System.out.printf("[listenerTask] date: %s, thread: %s, sessionKey: %d, sessionStatus: %s\n", new Date(), Thread.currentThread().getName(), session.getKey(), status);
    }
}

class PruebaObjects {
    SessionEventManager em;
    Map<Integer, String> database = new HashMap<>();

    public PruebaObjects() {
        this.em = new SessionEventManager();
    }

    public void createSession(int key, long timeout) {
        System.out.println("[persistentObjects] createSession: " + key);
        this.em.sessionCreated(new PruebaSession(key, timeout));
    }

    public void addListener(PruebaListener p) {
        System.out.println("[persistentObjects] addListener");
        this.em.addListener(p);
    }

    public void removeListener(PruebaListener p) {
        System.out.println("[persistentObjects] removeListener");
        this.em.removeListener(p);
    }

    public String load(int key) {
        System.out.println("[persistentObjects] load: " + key);
        this.em.update(key);
        return this.database.get(key);
    }

    public void store(int key, String value) {
        System.out.println("[persistentObjects] store: " + key + ", " + value);
        this.database.putIfAbsent(key, value);
        this.em.update(key);
    }

    public void status(int key) {
        this.em.printSessionStatus(key);
    }
}

