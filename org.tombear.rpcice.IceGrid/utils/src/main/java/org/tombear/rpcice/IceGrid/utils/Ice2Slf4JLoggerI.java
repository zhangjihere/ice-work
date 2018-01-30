package org.tombear.rpcice.IceGrid.utils;

import com.zeroc.Ice.Logger;

import org.slf4j.LoggerFactory;

/**
 * custom Ice Logger for support Slf4j
 * Created by ji.zhang on 1/26/18.
 */
public class Ice2Slf4JLoggerI implements com.zeroc.Ice.Logger {

    private final org.slf4j.Logger logger;

    public Ice2Slf4JLoggerI(String loggerName) {
        this.logger = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public void print(String message) {
        logger.info(message);
    }

    @Override
    public void trace(String category, String message) {
        logger.debug("{} {}", category, message);
    }

    @Override
    public void warning(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public String getPrefix() {
        return logger.getName();
    }

    @Override
    public Logger cloneWithPrefix(String prefix) {
        return new Ice2Slf4JLoggerI(prefix);
    }
}
