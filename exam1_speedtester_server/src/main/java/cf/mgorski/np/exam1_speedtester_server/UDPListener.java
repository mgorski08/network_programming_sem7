package cf.mgorski.np.exam1_speedtester_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

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
        StatCounter counter = new StatCounter();
        Thread statLoggerThread = null;
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        byte previous = 0;
        boolean validPrevious = false;
        while (true) {
            if (Thread.interrupted()) {
                break;
            }
            try {
                datagramSocket.receive(datagramPacket);
                String received = new String(buffer, 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
                if (received.equals("FINE") && statLoggerThread != null) {
                    statLoggerThread.interrupt();
                } else if (received.startsWith("SIZE:")) {
                    int newSize = Integer.parseInt(received.substring(5));
                    buffer = new byte[newSize];
                    datagramPacket = new DatagramPacket(buffer, buffer.length);
                    if (statLoggerThread == null || !statLoggerThread.isAlive()) {
                        statLoggerThread = new Thread(new StatLogger(counter), "UDP Stats");
                        statLoggerThread.start();
                    }
                    counter.reset();
                    validPrevious = false;
                    log.info("Set buffer to " + buffer.length + " bytes");
                } else {
                    if(validPrevious) {
                        counter.addLostPackets((byte)(buffer[0] - (previous + 1)));
                        previous = buffer[0];
                    } else {
                        previous = buffer[0];
                        validPrevious = true;
                    }
                    counter.addBytes(datagramPacket.getLength());
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
        if (statLoggerThread != null) {
            statLoggerThread.interrupt();
        }
        log.info("Closing UDP socket on port " + datagramSocket.getLocalPort());
        datagramSocket.close();
    }
}
