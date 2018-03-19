package me.java.progressbar.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBarItem {

    private String name;
    private int step;
    private AtomicInteger accum = new AtomicInteger(0);
    private long startMills;
    private long stopMills;

    private AtomicBoolean running = new AtomicBoolean(false);

    public ProgressBarItem(String name, int step) {
        this.name = name;
        this.step = step;
    }

    public int increment() {
        return accum.incrementAndGet();
    }

    public ProgressBarItem increment(int detal) {
         accum.addAndGet(detal);
         return this;
    }


    public long startMills() {
        return startMills;
    }

    public void setStartMills(long startMills) {
        this.startMills = startMills;
    }

    public int percentageOfItem() {
        return (int) Math.round(accum.doubleValue() / step * 100) ;
    }

    public int total() {
        return step;
    }

    public boolean stop() {
        return accum.intValue() >= step ? true : false;
    }

    public AtomicInteger getAccum() {
        return accum;
    }

    public String getName() {
        return name;
    }

}
