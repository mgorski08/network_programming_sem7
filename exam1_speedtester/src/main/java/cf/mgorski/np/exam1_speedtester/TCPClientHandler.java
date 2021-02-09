package cf.mgorski.np.exam1_speedtester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClientHandler implements Runnable {
    private static final Logger log = LogManager.getLogger(TCPListener.class);
    InputStreamReader is;
    OutputStreamWriter os;
    private final Socket socket;

    public TCPClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        is = new InputStreamReader(socket.getInputStream());
        os = new OutputStreamWriter(socket.getOutputStream());
        OutputStream os = socket.getOutputStream();
    }

    @Override
    public void run() {
        log.info("Connection from " + socket.getInetAddress());
        while (true) {
            try {
                is.read();
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
