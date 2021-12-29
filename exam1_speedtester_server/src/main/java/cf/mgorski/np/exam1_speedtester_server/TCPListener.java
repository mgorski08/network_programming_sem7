package cf.mgorski.np.exam1_speedtester_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;

public class TCPListener implements Runnable {
    private static final Logger log = LogManager.getLogger(TCPListener.class);

    private final ServerSocket serverSocket;
    private Thread tcpClientHandlerThread = null;

    public TCPListener(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(500);
    }

    @Override
    public void run() {
        log.info("Listening for TCP connections on port " + serverSocket.getLocalPort());
        while (true) {
            if (Thread.interrupted()) {
                break;
            }
            try {
                Socket socket = serverSocket.accept();
                if (tcpClientHandlerThread == null || !tcpClientHandlerThread.isAlive()) {
                    tcpClientHandlerThread = new Thread(new TCPClientHandler(socket), "TCP Handler (" + socket.getRemoteSocketAddress().toString() + ")");
                    tcpClientHandlerThread.start();
                } else {
                    OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
                    osw.write("BUSY");
                    osw.close();
                    log.info("Sent BUSY");
                }
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
