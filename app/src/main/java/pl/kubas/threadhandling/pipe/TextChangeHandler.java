package pl.kubas.threadhandling.pipe;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.PipedReader;

import pl.kubas.threadhandling.tools.CustomTimer;

public class TextChangeHandler implements Runnable {
    private final PipedReader reader;
    private Handler uiThread;
    private TextView outputTextView;
    private TextView timerDisplay;
    private CustomTimer timer;
    private final int ENCRYPTION_SHIFT = 3;
    private final int UPPER_BOUND = 127;

    public TextChangeHandler(Handler uiThread, PipedReader reader, TextView outputTextView, CustomTimer timer, TextView timerDisplay) {
        this.reader = reader;
        this.outputTextView = outputTextView;
        this.uiThread = uiThread;
        this.timerDisplay = timerDisplay;
        this.timer = timer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int letterASCII;
                while ((letterASCII = reader.read()) != -1) {
                    char letter = (char) ((letterASCII + ENCRYPTION_SHIFT) % UPPER_BOUND);
                    Log.i(PipeActivity.TAG, "letter: " + letter);
                    timer.finishCounting();
                    addLetter(outputTextView, letter);
                }
            } catch (IOException ioException) {
                Log.e(PipeActivity.TAG, ioException.getStackTrace().toString());
            }
        }
    }

    private void addLetter(TextView outputTextView, char letter) {
        String textToDisplay = outputTextView.getText() == null ? "" : outputTextView.getText().toString();
        textToDisplay += letter;
        refreshTextView(outputTextView, textToDisplay, timer, timerDisplay);

    }

    private void refreshTextView(final TextView outputTextView, final String textToDisplay, final CustomTimer timer, final TextView timerDisplay) {
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                outputTextView.setText(textToDisplay);
                timerDisplay.setText(timer.getMeasurementMS() + " ms");
            }
        });
    }

}
