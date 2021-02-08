package cf.mgorski.np.exam1_speedtester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.SocketException;

class Application {
    static Logger log = LogManager.getLogger(Application.class);
    public static void main(String[] args) throws SocketException {
//        new Thread(new UDPEchoer(12345)).start();
        log.error("Test error");
    }
}