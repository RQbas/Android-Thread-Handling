package pl.kubas.threadhandling.messages.bidirectional;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;

public class BidirectionalMessageActivity extends AppCompatActivity {
    public static final String TAG = "BidirectionalMessageActivity";
    public static final int SHOW_ORIGINAL_COLOR = 0;
    public static final int SHOW_NEW_COLOR = 1;
    private Handler uiHandler;
    private BackgroundThread backgroundThread;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.progress_textview)
    TextView textView;

    @BindView(R.id.button_action)
    CardView actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidirectional_message);
        ButterKnife.bind(this);
        initToolbar();
        initUIHandler();
        initBackgroundThread();
        initUIComponents();

    }

    private void initUIHandler() {
        int initialColor = ContextCompat.getColor(this, R.color.purple);
        int finalColor = ContextCompat.getColor(this, R.color.blue);
        uiHandler = new UIHandler(progressBar, initialColor, finalColor);
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
        super.onDestroy();
        backgroundThread.exit();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initBackgroundThread() {
        this.backgroundThread = new BackgroundThread(uiHandler);
        this.backgroundThread.start();
    }

    private void initUIComponents() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundThread.performOperation(progressBar);
            }
        });
    }

    private static class UIHandler extends Handler {
        private final WeakReference<ProgressBar> progressBar;
        private final int finalColor;
        private final int initialColor;

        UIHandler(ProgressBar progressBar, int initialColor, int finalColor) {
            this.progressBar = new WeakReference<>(progressBar);
            this.initialColor = initialColor;
            this.finalColor = finalColor;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ORIGINAL_COLOR:
                    progressBar.get().getProgressDrawable().setColorFilter(initialColor, PorterDuff.Mode.SRC_IN);
                    progressBar.get().setProgress(0);

                    break;
                case SHOW_NEW_COLOR:
                    progressBar.get().getProgressDrawable().setColorFilter(finalColor, PorterDuff.Mode.SRC_IN);
                    break;
            }
        }
    }


}
