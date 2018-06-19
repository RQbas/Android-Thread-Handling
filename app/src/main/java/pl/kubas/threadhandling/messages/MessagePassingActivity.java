package pl.kubas.threadhandling.messages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;

public class MessagePassingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private LooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_passing);
        ButterKnife.bind(this);
        initToolbar();
        initThread();
        initButton();
    }

    private void initButton() {
    }

    @Override
    protected void onDestroy() {
        looperThread.mHandler.getLooper().quit();
        super.onDestroy();
    }

    private void initThread() {
        this.looperThread = new LooperThread();
        this.looperThread.start();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }
}
