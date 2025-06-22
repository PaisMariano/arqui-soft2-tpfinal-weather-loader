package com.edu.unq.arqsoft2.weatherloaderconn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    
    private LogUtil() {
        // Private constructor to prevent instantiation
    }
    
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    public static void logWarn(Logger logger, String message) {
        logger.warn(message);
    }
    
    public static void logInfo(Logger logger, String message) {
        logger.info(message);
    }
    
    public static void logDebug(Logger logger, String message) {
        logger.debug(message);
    }
}
