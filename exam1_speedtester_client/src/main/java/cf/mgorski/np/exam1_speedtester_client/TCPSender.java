package cf.mgorski.np.exam1_speedtester_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TCPSender implements Runnable {
    private static final Logger log = LogManager.getLogger(TCPSender.class);
    private final int size;
    private final Socket socket;
    InetSocketAddress server;

    public TCPSender(InetSocketAddress server, int size, boolean nagle) throws IOException {
        this.server = server;
        this.size = size;
        socket = new Socket(server.getAddress(), server.getPort());
        socket.setTcpNoDelay(!nagle);
    }

    @Override
    public void run() {
        String sizeMessage = "SIZE:" + size;
        String fineMessage = "FINE";
        try {
            log.info("Sending TCP segments to " + server.toString());
            byte[] buffer = new byte[size];
            Arrays.fill(buffer, (byte) 0);
            try {
                socket.getOutputStream().write(sizeMessage.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.error(e);
                return;
            }
            while (true) {
                try {
                    Arrays.fill(buffer, (byte) (buffer[0] + 1));
//                    Thread.sleep(0, 1000);
                    socket.getOutputStream().write(buffer);
                    if (Thread.interrupted()) {
                        break;
                    }
                } catch (SocketTimeoutException e) {
                    if (Thread.interrupted()) {
                        break;
                    }
                } catch (Exception e) {
                    log.error(e);
                    break;
                }
            }
        } finally {
            try {
                socket.getOutputStream().write(fineMessage.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.error(e);
            }
            log.info("Closing TCP socket on port " + socket.getLocalPort());
            try {
                socket.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
