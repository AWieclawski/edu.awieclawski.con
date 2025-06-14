package edu.awieclawski.conn;

import edu.awieclawski.conn.logger.CustomLogger;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalNetworkUtility {

    private static final Logger logger = CustomLogger.getLogger(LocalNetworkUtility.class);
    private final static String PROTOCOL = "http";
    private final static int[] PORTS = new int[]{8080, 80};
    private final static int TIME_OUT = 100;

    private static Integer lastIpNumber;

    public static void main(String[] args) {
        String ip;
        try {
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            assert logger != null;
            logger.log(Level.INFO, String.format("This machine ip: [%s]", ip));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (ip != null && !ip.isEmpty()) {
            String addr = ip.substring(0, ip.lastIndexOf(".") + 1);
            AtomicReference<URL> urlAtomicReference = new AtomicReference<>();
            for (int i = 1; i < 255; i++) {
                lastIpNumber = i;
                Arrays.stream(PORTS).forEach(
                        port -> {
                            urlAtomicReference.set(buildUrl(addr, port));
                            makeConnection(urlAtomicReference.get());
                        });
            }
        }
    }

    private static URL buildUrl(String addr, final int port) {
        try {
            final String addrIp = addr + lastIpNumber;
            final String fullUrl = PROTOCOL + "://" + addrIp + ":" + port;
            return new URL(fullUrl);
        } catch (Exception e) {
            assert logger != null;
            logger.log(Level.WARNING, String.format("%s err: %s", addr, e.getMessage()));
        }
        return null;
    }

    private static void makeConnection(URL url) {
        try {
            assert url != null;
            doConnection(url);
            assert logger != null;
            logger.log(Level.INFO, String.format("OK: %s", url));
        } catch (Exception e) {
            assert logger != null;
            logger.log(Level.WARNING, String.format("%s failed! | %s", url, e.getMessage()));
        }
    }

    private static void doConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.connect();

        int code = connection.getResponseCode();
        String fullUrl = connection.getURL().toString();
        if (logger != null && logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, String.format("IP: %s - code: %d", fullUrl != null ? fullUrl : "", code));

            Map<String, List<String>> fields = connection.getHeaderFields();
            fields.entrySet().stream()
                    .filter(Objects::nonNull)
                    .forEach(e -> logger.log(Level.INFO, String.format(" - k: %s  | v: %s",
                            e.getKey(), e.getValue())));
        }
    }


}
