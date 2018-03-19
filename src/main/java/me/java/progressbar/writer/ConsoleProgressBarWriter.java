package me.java.progressbar.writer;

import me.java.progressbar.model.ProgressBar;
import me.java.progressbar.model.ProgressBarItem;

import java.io.IOException;
import java.io.Writer;

public class ConsoleProgressBarWriter implements ProgressBarWriter {
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String DETAL = "=";
    private static final String BLANK_CHAR = " ";
    private static final String TARGET_CHAR = ">";
    private static final int DETALNUM = 20;

    private Writer writer;

    public ConsoleProgressBarWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void before(ProgressBar progressBar) throws IOException {
        writer.write(progressBar.getName()+":"+"\n");
        writer.flush();
    }

    @Override
    public void write(ProgressBar progressBar) throws IOException {
        ProgressBarItem progressBarItem = progressBar.getCurrentItem();
        Long currentMills = System.currentTimeMillis();
        writer.write("\r");
        writer.write("progressBar progress" + accumBlock(progressBar.percentageOfProgressBar())+progressBar.percentageOfProgressBar()+"% "+" ( " + progressBar.format(currentMills - progressBar.startMills())+" ) ");
        writer.write("progressBarItem progress :"+progressBarItem.getName() +" "+accumBlock(progressBarItem.percentageOfItem())+progressBarItem.percentageOfItem()+"%"+" ( " + progressBar.format(currentMills - progressBarItem.startMills())+" ) ");
        writer.flush();
    }

    @Override
    public void after(ProgressBar progressBar) throws IOException {
        writer.write("\n");
        writer.flush();
        writer.close();
    }

    public String accumBlock(int percentage){
        StringBuffer accumStr = new StringBuffer(LEFT_BRACKET);
        int equalityCharNum = (int)Math.round((percentage / 100.0 *DETALNUM)) ;
        for (int i = 0 ; i < equalityCharNum ; i++){
            accumStr.append(DETAL);
        }
        accumStr.append(TARGET_CHAR);
        for (int i = 0 ;i < DETALNUM - equalityCharNum ; i++){
            accumStr.append(BLANK_CHAR);
        }

        accumStr.append(RIGHT_BRACKET);
        return accumStr.toString();
    }

}
