package cf.mgorski.np.exam1_speedtester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPListener implements Runnable {
    private static final Logger log = LogManager.getLogger(UDPListener.class);

    private final DatagramSocket datagramSocket;

    public UDPListener(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
        datagramSocket.setSoTimeout(500);
    }

    @Override
    public void run() {
        log.info("Listening for UDP datagrams on port " + datagramSocket.getLocalPort());
        byte[] buffer = new byte[4096];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
                datagramSocket.receive(datagramPacket);
            } catch (SocketTimeoutException e) {
                if (Thread.interrupted()) {
                    break;
                }
            } catch (IOException e) {
                log.error(e);
                break;
            }
        }
        log.info("Closing UDP socket on port " + datagramSocket.getLocalPort());
        datagramSocket.close();
    }
}
