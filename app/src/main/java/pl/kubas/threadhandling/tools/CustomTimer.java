package pl.kubas.threadhandling.tools;

public class CustomTimer {
    private long startTime;
    private long endTime;


    public void startCounting() {
        this.startTime = System.currentTimeMillis();
    }

    public void finishCounting() {
        this.endTime = System.currentTimeMillis();
    }

    public long getMeasurementMS() {
        return endTime - startTime;
    }

}
