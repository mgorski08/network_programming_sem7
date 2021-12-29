package cf.mgorski.np.exam1_speedtester_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

class Application {
    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Speedtester server");

        System.out.println("Please provide data for the server");
        final int port = UserInput.readInt("Port", 12345);

        try {
            Thread udpListenerThread = new Thread(new UDPListener(port), "UDP("+port+")");
            Thread tcpListenerThread = new Thread(new TCPListener(port), "TCP("+port+")");
            Thread discoveryListenerThread = new Thread(new DiscoveryListener(port), "DISCOVERY("+port+")");
            udpListenerThread.start();
            tcpListenerThread.start();
            discoveryListenerThread.start();
            System.in.read();
            udpListenerThread.interrupt();
            tcpListenerThread.interrupt();
            discoveryListenerThread.interrupt();
            try {
                udpListenerThread.join();
            } catch (InterruptedException ignored) {}
            try {
                tcpListenerThread.join();
            } catch (InterruptedException ignored) {}
            try {
                discoveryListenerThread.join();
            } catch (InterruptedException ignored) {}
        } catch (IOException e) {
            log.error(e);
        }
        log.info("Exiting");
    }
}