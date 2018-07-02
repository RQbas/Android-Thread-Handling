package pl.kubas.threadhandling.messages.unidirectional;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class LooperThread extends Thread { //Definition of worker thread acting as a message queue consumer.
    private CustomHandler mHandler;
    private Handler uiThread;
    private ProgressBar progressBar;
    private TextView textView;

    LooperThread(ProgressBar progressBar, TextView textView, Handler uiThread) { //UI components
        this.progressBar = progressBar;
        this.textView = textView;
        this.uiThread = uiThread;
    }

    @Override
    public void run() {
        Looper.prepare(); //Binding Looper (plus MessageQueue) with this thread.
        /* Configuration of handler procedure which will be used by the producer. Default Handler constructor in order to bind to
         looper of current thread. That's why it is called after prepare method. In other way there would be no object to bind with.
         */
        mHandler = new CustomHandler(progressBar, textView, uiThread);
        Looper.loop(); //Starts message sending from message queue to consumer thread. Blocking call - run is blocked
    }

    public Handler getmHandler() {
        return mHandler;
    }


    private static class CustomHandler extends Handler {
        private final WeakReference<ProgressBar> progressBar;
        private final WeakReference<TextView> textView;
        private final WeakReference<Handler> uiThread;
        private int progressStatus = 0;

        CustomHandler(ProgressBar progressBar, TextView textView, Handler uiThread) {
            this.progressBar = new WeakReference<>(progressBar);
            this.textView = new WeakReference<>(textView);
            this.uiThread = new WeakReference<>(uiThread);
        }

        @Override
        public void handleMessage(Message msg) { //Callback when message is sent to worker thread
            if (msg.what == 0) {
                fillProgressbar(progressBar.get(), textView.get(), uiThread.get());
            }
        }

        private void fillProgressbar(final ProgressBar progressBar, final TextView textView, final Handler uiThread) {
            progressStatus = 0;
            while (progressStatus < 100) {
                progressStatus += 1;
                uiThread.post(new Runnable() {
                    public void run() {
                        progressBar.setProgress(progressStatus);
                        textView.setText(progressStatus + "/" + progressBar.getMax());
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
