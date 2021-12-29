package cf.mgorski.np.exam1_speedtester_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UDPSender implements Runnable {
    private static final Logger log = LogManager.getLogger(UDPSender.class);

    private final DatagramSocket datagramSocket = new DatagramSocket();
    private final InetSocketAddress server;
    private final int size;

    public UDPSender(InetSocketAddress server, int size) throws SocketException {
        this.server = server;
        this.size = size;
        datagramSocket.setSoTimeout(500);
    }

    @Override
    public void run() {
        byte[] sizeMessage = ("SIZE:" + size).getBytes(StandardCharsets.UTF_8);
        byte[] fineMessage = "FINE".getBytes(StandardCharsets.UTF_8);
        try {
            log.info("Sending UDP datagrams to " + server.toString());
            byte[] buffer = new byte[size];
            Arrays.fill(buffer, (byte) 0);
            try {
                datagramSocket.send(new DatagramPacket(sizeMessage, sizeMessage.length, server));
            } catch (IOException e) {
                log.error(e);
                return;
            }
            while (true) {
                try {
                    Arrays.fill(buffer, (byte) (buffer[0] + 1));
//                    Thread.sleep(0, 1000);
                    datagramSocket.send(new DatagramPacket(buffer, buffer.length, server));
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
                datagramSocket.send(new DatagramPacket(fineMessage, fineMessage.length, server));
            } catch (IOException e) {
                log.error(e);
            }
            log.info("Closing UDP socket on port " + datagramSocket.getLocalPort());
            datagramSocket.close();
        }
    }
}
