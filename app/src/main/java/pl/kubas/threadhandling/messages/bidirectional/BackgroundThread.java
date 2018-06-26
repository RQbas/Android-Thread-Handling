package pl.kubas.threadhandling.messages.bidirectional;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class BackgroundThread extends Thread {
    private BackgroundHandler backgroundHandler;
    private Handler uiHandler;

    public BackgroundThread(Handler uiHandler) {
        this.uiHandler = uiHandler;
    }

    public void run() {
        Looper.prepare();
        backgroundHandler = new BackgroundHandler();
        Looper.loop();
    }

    public void performOperation() {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                Message uiMsg = uiHandler.obtainMessage(BidirectionalMessageActivity.SHOW_ORIGINAL_COLOR, 0, 0, null);
                uiHandler.sendMessage(uiMsg);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                uiMsg = uiHandler.obtainMessage(BidirectionalMessageActivity.SHOW_NEW_COLOR, 0, 0, null);
                uiHandler.sendMessage(uiMsg);
            }
        });
    }

    public void exit() {
        backgroundHandler.getLooper().quit();
    }

    private static class BackgroundHandler extends Handler {

    }
}
