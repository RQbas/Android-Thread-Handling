package pl.kubas.threadhandling.messages.unidirectional;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;

public class MessagePassingActivity extends AppCompatActivity {
    public static final String TAG = "MessagePassingActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.progress_textview)
    TextView textView;

    @BindView(R.id.button_action)
    CardView actionButton;

    private LooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_passing);
        ButterKnife.bind(this);
        initToolbar();
        initThread();
        initUIComponents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        looperThread.getmHandler().getLooper().quit(); //End of background thread, stop message sending, release Looper.loop() from blocking (run ends)
        super.onDestroy();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initThread() {
        Handler uiThread = new Handler(Looper.getMainLooper());
        this.looperThread = new LooperThread(progressBar, textView, uiThread); //Initialization and start of worker thread
        this.looperThread.start();
    }

    private void initUIComponents() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (looperThread.getmHandler() != null) { //Checking handler initialization state
                    Message msg = looperThread.getmHandler().obtainMessage(0); //Message initialization with what argument set to 0
                    looperThread.getmHandler().sendMessage(msg); //Putting message in message queue
                }
            }
        });
    }


}
