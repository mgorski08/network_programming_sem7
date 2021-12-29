package cf.mgorski.np.exam1_speedtester_server;

public class StatCounter {
    private long bitCounter;
    private long lostPacketCounter;
    private long timeCounter;

    public void reset_timer() {
        timeCounter = System.currentTimeMillis();
    }

    public void reset_data() {
        bitCounter = 0;
    }

    public void reset_lost() {
        lostPacketCounter = 0;
    }

    public void reset() {
        reset_data();
        reset_timer();
        reset_lost();
    }

    public double get_Kib() {
        return (double) bitCounter / 1024;
    }

    public String get_Kib_f() {
        return String.format("%.2f", get_Kib()) + " Kib";
    }

    public double get_KiB() {
        return (double) bitCounter / (1024 * 8);
    }

    public String get_KiB_f() {
        return String.format("%.2f", get_KiB()) + " KiB";
    }

    public double get_Mib() {
        return (double) bitCounter / (1024 * 1024);
    }

    public String get_Mib_f() {
        return String.format("%.2f", get_Mib()) + " Mib";
    }

    public double get_MiB() {
        return (double) bitCounter / (1024 * 1024 * 8);
    }

    public String get_MiB_f() {
        return String.format("%.2f", get_MiB()) + " MiB";
    }

    public double get_Gib() {
        return (double) bitCounter / (1024 * 1024 * 1024);
    }

    public String get_Gib_f() {
        return String.format("%.2f", get_Gib()) + " Gib";
    }

    public double get_GiB() {
        return (double) bitCounter / (1024.0 * 1024.0 * 1024.0 * 8.0);
    }

    public String get_GiB_f() {
        return String.format("%.2f", get_GiB()) + " GiB";
    }

    public double get_seconds() {
        return (double) (System.currentTimeMillis() - timeCounter) / 1000;
    }

    public String get_seconds_f() {
        return String.format("%.2f", get_seconds()) + " seconds";
    }

    public String get_data_f() {
        if (get_GiB() > 1) {
            return get_GiB_f();
        } else if (get_MiB() > 1) {
            return get_MiB_f();
        } else {
            return get_KiB_f();
        }
    }

    public String get_speed_f() {
        double seconds = get_seconds();
        double data = get_Kib();
        if (seconds == 0) {
            return "---";
        } else {
            double speed = data / seconds;
            if (speed > (1024 * 1024)) {
                return String.format("%.2f", speed / (1024 * 1024)) + " Gibps";
            } else if (speed > 1024) {
                return String.format("%.2f", speed / 1024) + " Mibps";
            } else {
                return String.format("%.2f", speed) + " Kibps";
            }
        }
    }

    public void addBits(long bits) {
        synchronized (this) {
            bitCounter += bits;
        }
    }

    public void addBytes(long bytes) {
        synchronized (this) {
            bitCounter += bytes * 8;
        }
    }

    public void addLostPackets(long lost) {
        synchronized (this) {
            lostPacketCounter += lost;
        }
    }

    public long getLostPackets() {
        return lostPacketCounter;
    }

}
