package pl.kubas.threadhandling.messages.bidirectional;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;

public class BackgroundThread extends Thread {
    private BackgroundHandler backgroundHandler;
    private Handler uiHandler;
    private int progressStatus;

    public BackgroundThread(Handler uiHandler) {
        this.uiHandler = uiHandler;
    }

    public void run() {
        Looper.prepare();
        backgroundHandler = new BackgroundHandler();
        Looper.loop();
    }

    public void performOperation(final ProgressBar progressBar) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                Message uiMsg = BackgroundThread.this.uiHandler.obtainMessage(BidirectionalMessageActivity.SHOW_ORIGINAL_COLOR, 0, 0, null);
                BackgroundThread.this.uiHandler.sendMessage(uiMsg);
                fillProgressbar(progressBar);
                uiMsg = BackgroundThread.this.uiHandler.obtainMessage(BidirectionalMessageActivity.SHOW_NEW_COLOR, 0, 0, null);
                BackgroundThread.this.uiHandler.sendMessage(uiMsg);
            }
        });
    }

    private void fillProgressbar(final ProgressBar progressBar) {
        progressStatus = 0;
        while (progressStatus < 100) {
            progressStatus += 2;
            uiHandler.post(new Runnable() {
                public void run() {
                    progressBar.setProgress(progressStatus);
                }
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        backgroundHandler.getLooper().quit();
    }

    private static class BackgroundHandler extends Handler {

    }
}
