package cf.mgorski.np.exam1_speedtester_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatLogger implements Runnable {
    private static final Logger log = LogManager.getLogger(StatLogger.class);
    private final StatCounter counter;

    public StatLogger(StatCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        while (true) {
            log.info("Received " + counter.get_data_f() + " in " + counter.get_seconds_f() + " at speed " + counter.get_speed_f() + ", lost " + counter.getLostPackets());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
