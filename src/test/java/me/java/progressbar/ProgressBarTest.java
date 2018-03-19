package me.java.progressbar;

import me.java.progressbar.model.ProgressBar;
import me.java.progressbar.model.ProgressBarItem;
import me.java.progressbar.writer.ConsoleProgressBarWriter;

import java.io.IOException;
import java.io.PrintWriter;

public class ProgressBarTest {
    public static void main(String... args) throws IOException {
        ProgressBar progressBar = new ProgressBar(new ConsoleProgressBarWriter(new PrintWriter(System.out)), "testProgressBar");
        progressBar.step(new ProgressBarItem("item1", 100)).step(new ProgressBarItem("item2", 200)).step(new ProgressBarItem("item3", 500));
        progressBar.start();
        step1(progressBar,100);
        step1(progressBar,200);
        step1(progressBar,500);
        progressBar.close();


    }

    private static void  step1(ProgressBar progressBar ,int step) throws IOException {

        for (int i = 0; i < step; i++) {
            try {
                if (progressBar.isStoping()) {
                    return ;
                }
                Thread.sleep(10L);
                progressBar.increment(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
