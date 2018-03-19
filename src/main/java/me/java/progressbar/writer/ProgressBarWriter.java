package me.java.progressbar.writer;

import me.java.progressbar.model.ProgressBar;

import java.io.IOException;

public interface ProgressBarWriter {

    void before (ProgressBar progressBar) throws IOException;

    void write (ProgressBar progressBar) throws IOException;

    void after (ProgressBar progressBar) throws IOException;
}
