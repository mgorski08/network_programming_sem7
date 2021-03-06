package cf.mgorski.np.exam1_speedtester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;

public class TCPListener implements Runnable {
    private static final Logger log = LogManager.getLogger(TCPListener.class);

    private final ServerSocket serverSocket;

    public TCPListener(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(500);
    }

    @Override
    public void run() {
        log.info("Listening for TCP connections on port " + serverSocket.getLocalPort());
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                TCPClientHandler tcpClientHandler = new TCPClientHandler(socket);
            } catch (SocketTimeoutException e) {
                if (Thread.interrupted()) {
                    break;
                }
            } catch (IOException e) {
                log.error(e);
                break;
            }
        }
        log.info("Closing TCP socket on port " + serverSocket.getLocalPort());
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error(e);
        }
    }
}
