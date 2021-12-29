package cf.mgorski.np.exam1_speedtester_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClientHandler implements Runnable {
    private static final Logger log = LogManager.getLogger(TCPListener.class);
    private final Socket socket;

    public TCPClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        socket.setSoTimeout(500);
    }

    @Override
    public void run() {
        log.info("Connection from " + socket.getInetAddress());
        InputStreamReader ir;
        long size;
        StatCounter counter = new StatCounter();
        Thread statLoggerThread = new Thread(new StatLogger(counter), "TCP stats");
        try {
            ir = new InputStreamReader(socket.getInputStream());
            char[] buffer = new char[5];
            ir.read(buffer, 0, 5);
            if (new String(buffer).equals("SIZE:")) {
                char c = (char) ir.read();
                StringBuilder num = new StringBuilder();
                while (Character.isDigit(c)) {
                    num.append(c);
                    c = (char) ir.read();
                }
                size = Integer.parseInt(num.toString());
            } else {
                return;
            }
            counter.reset();
            statLoggerThread.start();
            while (true) {
                if (Thread.interrupted()) {
                    break;
                }
                try {
                    if(ir.read() == -1) return;
                    counter.addBytes(ir.skip(size-1));
                } catch (SocketTimeoutException e) {
                    if (Thread.interrupted()) {
                        break;
                    }
                } catch (IOException e) {
                    log.error(e);
                    break;
                }
            }

        } catch (Exception e) {
            log.error(e);
        } finally {
            log.info("Closing TCP socket on remote port " + socket.getPort());
            statLoggerThread.interrupt();
            try {
                socket.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
