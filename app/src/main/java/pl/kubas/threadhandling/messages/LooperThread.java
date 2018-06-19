package pl.kubas.threadhandling.messages;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LooperThread extends Thread {
    public Handler mHandler;

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    fillProgressbar();
                }
            }


        };
        Looper.loop();
    }


    private void fillProgressbar() {
    }
}
