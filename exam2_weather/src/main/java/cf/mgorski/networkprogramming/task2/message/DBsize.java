package cf.mgorski.networkprogramming.task2.message;

public class DBsize {

    double days;
    long samples;

    public DBsize(double days, long samples) {
        this.days = days;
        this.samples = samples;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public long getSamples() {
        return samples;
    }

    public void setSamples(long samples) {
        this.samples = samples;
    }
}
