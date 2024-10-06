package edu.awieclawski.con;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlConnection {

    static final Logger logger = edu.awieclawski.con.log.CustomLogger.getLogger(UrlConnection.class);

    public static void main(String[] args) throws MalformedURLException {
        String addr = "http://10.10.10.";

        for (int i = 1; i < 255; i++) {
            URL url = new URL(addr + i);
            try {
                doConnection(url);
            } catch (Exception e) {
//                logger.log(Level.WARNING, String.format("err: %s", e.getMessage()));
            }
        }


    }

    private static void doConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(500);
        connection.setReadTimeout(500);
        connection.connect();

        int code = connection.getResponseCode();
        String fullUrl = connection.getURL().toString();
        logger.log(Level.INFO, String.format("IP: %s - code: %d", fullUrl, code));

        Map<String, List<String>> flds = connection.getHeaderFields();
        flds.entrySet().stream()
                .filter(Objects::nonNull)
                .forEach(e -> logger.log(Level.INFO, " - k: " + e.getKey() + " | v: " + e.getValue() + " "));
    }

}
