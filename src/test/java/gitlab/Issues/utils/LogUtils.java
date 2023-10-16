package gitlab.Issues.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    // initializes a logger instance for the "LogUtils" class using Apache Log4j
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    // logs an informational message using the logger instance
    public static void logInfo(String text){
        logger.info(text);
    }
}
