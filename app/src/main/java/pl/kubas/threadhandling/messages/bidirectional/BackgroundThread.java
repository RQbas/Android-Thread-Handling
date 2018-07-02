package pl.kubas.threadhandling.messages.bidirectional;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;

public class BackgroundThread extends Thread {
    private Handler backgroundHandler;
    private Handler uiHandler;
    private int progressStatus;

    BackgroundThread(Handler uiHandler) {
        this.uiHandler = uiHandler;
    }

    public void run() { //Associate Looper with the thread
        Looper.prepare();
        backgroundHandler = new Handler(); //Handler processes only runnables. It is not required to implement handleMessage(msg)
        Looper.loop();
    }

    public void performOperation(final ProgressBar progressBar) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() { //Post long task to be executed in the background
                Message uiMSG;
                //Create message with only SHOW_ORIGINAL_COLOR argument
                uiMSG = BackgroundThread.this.uiHandler.obtainMessage(BidirectionalMessageActivity.SHOW_ORIGINAL_COLOR, 0, 0, null);
                BackgroundThread.this.uiHandler.sendMessage(uiMSG); //Send start message to UI thread

                fillProgressbar(progressBar); //Fill progress bar as a long time operation
                //Message with information SHOW_NEW_COLOR as a end of long time operation
                uiMSG = BackgroundThread.this.uiHandler.obtainMessage(BidirectionalMessageActivity.SHOW_NEW_COLOR, 0, 0, null);
                BackgroundThread.this.uiHandler.sendMessage(uiMSG); //Message with end result is sent
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
    } //Quit the looper so thread can finish
}
