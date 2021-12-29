package cf.mgorski.np.exam1_speedtester_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class DiscoveryListener implements Runnable {
    private static final Logger log = LogManager.getLogger(DiscoveryListener.class);

    private final MulticastSocket multicastSocket;
    private final int port;

    public DiscoveryListener(int port) throws IOException {
        this.port = port;
        multicastSocket = new MulticastSocket(65000);
        multicastSocket.joinGroup(new InetSocketAddress("224.0.0.10", 65000), null);
        multicastSocket.setSoTimeout(500);
    }

    @Override
    public void run() {
        log.info("Listening for DISCOVERY UDP datagrams on port " + multicastSocket.getLocalPort());
        byte[] buffer = new byte[4096];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
                multicastSocket.receive(datagramPacket);
                String received = new String(buffer, 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
                if (received.equals("DISCOVERY") || received.equals("DISCOVER")) {
                    byte[] offer = ("OFFER:"+port).getBytes(StandardCharsets.UTF_8);
                    multicastSocket.send(new DatagramPacket(offer, offer.length, new InetSocketAddress("224.0.0.10", 65000)));
                    log.info("Sent OFFER to multicast group");
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
        log.info("Closing DISCOVERY UDP socket on port " + multicastSocket.getLocalPort());
        multicastSocket.close();
    }
}
