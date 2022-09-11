package com.palg.tp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggerSessionListener.class);

    @Override
    public void sessionClosed(long key) {
        logger.info("Session %s is closed".formatted(key));
    }

    @Override
    public void sessionStillClosed(long key) {
        logger.info("Session %s still closed".formatted(key));
    }

    @Override
    public void sessionOpened(long key) {
        logger.info("Session %s is opened".formatted(key));
    }

    @Override
    public void sessionStillOpened(long key) {
        logger.info("Session %s is still opened".formatted(key));
    }
}
