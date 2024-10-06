package edu.awieclawski.con.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
 */
public class CustomLogger {

    public static Logger getLogger(Class<?> clazz) {
        InputStream stream = clazz.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            return Logger.getLogger(clazz.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
