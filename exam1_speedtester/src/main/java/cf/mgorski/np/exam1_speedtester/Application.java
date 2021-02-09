package cf.mgorski.np.exam1_speedtester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.SocketException;

class Application {
    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Speedtester server");

        System.out.println("Please provide data for the server");
        final int port = UserInput.readInt("Port", 12345);

        try {
            Thread udpEchoerThread = new Thread(new UDPEchoer(port), "UDP("+port+")");
            udpEchoerThread.start();
            Thread.sleep(5000);
            udpEchoerThread.interrupt();
        } catch (SocketException | InterruptedException e) {
            log.error(e);
        }
    }
}