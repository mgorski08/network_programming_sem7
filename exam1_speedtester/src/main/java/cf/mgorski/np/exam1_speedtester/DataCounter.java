package cf.mgorski.np.exam1_speedtester;

public class DataCounter {
    private long bitCounter = 0;

    public String get_Kib() {
        return bitCounter / 1024 + " Kib";
    }

    public String get_KiB() {
        return bitCounter / 8192 + "KiB";
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

}
