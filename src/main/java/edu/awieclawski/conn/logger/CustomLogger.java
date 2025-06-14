package edu.awieclawski.conn.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
 * https://codingtechroom.com/question/change-logger-output-color-eclipse
 */
public class CustomLogger {

    public static final String PROP_FILE = "logging.properties";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static Logger getLogger(Class<?> clazz) {
        String packName = clazz.getPackage().getName();
        int lastIndex = packName.lastIndexOf(".");
        String parentPackName = packName.substring(0, lastIndex);
        Logger mainLogger = Logger.getLogger(parentPackName);
        mainLogger.setUseParentHandlers(false);
        ConsoleHandler handler = getConsoleHandler(clazz);
        mainLogger.addHandler(handler);
        return Logger.getLogger(clazz.getName());
    }


    private static ConsoleHandler getConsoleHandler(Class<?> clazz) {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private final String format = getFormat(clazz);

            @Override
            public synchronized String format(LogRecord lr) {

                String customFormat;

                switch (lr.getLevel().getName()) {
                    case "INFO":
                        customFormat = ANSI_GREEN + format + ANSI_RESET;
                        break;
                    case "WARNING":
                    case "ERROR":
                        customFormat = ANSI_RED + format + ANSI_RESET;
                        break;
                    default:
                        customFormat = ANSI_WHITE + format + ANSI_RESET;
                        break;
                }

                return String.format(customFormat,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }

        });
        return handler;
    }

    private static String getFormat(Class<?> clazz) {
        Properties properties;
        properties = new Properties();
        InputStream inputStream = clazz.getClassLoader().
                getResourceAsStream(PROP_FILE);
        try {
            properties.load(inputStream);
            return properties.getProperty("java.util.logging.SimpleFormatter.format");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
