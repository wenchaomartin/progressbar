package me.java.progressbar.model;

import me.java.progressbar.writer.ProgressBarWriter;

import java.io.IOException;
import java.util.ArrayDeque;


public class ProgressBar {
    private ArrayDeque<ProgressBarItem> itemQueue = new ArrayDeque<>();
    private ArrayDeque<ProgressBarItem> trashQueue = new ArrayDeque<>();
    private ProgressBarWriter writer;
    private String name;
    private Long startMills;

    public ProgressBar(ProgressBarWriter writer, String name) {
        this.writer = writer;
        this.name = name;
    }

    public ProgressBar step(ProgressBarItem progressBarItem) {
        itemQueue.add(progressBarItem);
        return this;
    }

    public int percentageOfProgressBar() {
        return (int) Math.round((double) currentAccum() / total() * 100);
    }

    public int total() {
        int total = 0;
        for (ProgressBarItem item : itemQueue) {
            total += item.total();
        }
        for (ProgressBarItem item : trashQueue) {
            total += item.total();
        }
        return total;
    }

    public int currentAccum() {
        int current = 0;

        for (ProgressBarItem item : trashQueue) {
            current += item.total();
        }
        if (itemQueue.peek() != null) {
            current += itemQueue.peek().getAccum().intValue();
        }
        return current;
    }

    public void start() throws IOException {
        ProgressBarItem progressBarItem = getCurrentItem();
        long current = System.currentTimeMillis();
        progressBarItem.setStartMills(current);
        this.setStartMills(current);

        writer.before(this);
    }

    public void increment(int detal) throws IOException {
        if (isStoping()) {
            return;
        }
        ProgressBarItem progressBarItem = getCurrentItem();
        progressBarItem.increment(detal);
        if (progressBarItem.getAccum().intValue() >= progressBarItem.total()) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writer.write(this);
            itemQueue.remove();
            trashQueue.add(progressBarItem);
            if(itemQueue.peek() != null){
                itemQueue.peek().setStartMills(System.currentTimeMillis());
            }


        } else {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writer.write(this);
        }
    }

    public ProgressBarItem getCurrentItem() {
        return itemQueue.peek();
    }

    public boolean isStoping() {
        return itemQueue.size() <= 0 ? true : false;
    }

    public String getName() {
        return name;
    }

    public void close() throws IOException {

        writer.after(this);
    }

    public void setStartMills(Long startMills) {
        this.startMills = startMills;
    }

    public Long startMills() {
        return startMills;
    }

    public String format(Long timeMills) {
        Long seconds = timeMills / 1000 % 60;
        Long minutes = timeMills / 1000 / 60 % 60;
        Long hours = timeMills / 1000 / (60 * 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
