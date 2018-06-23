package pl.kubas.threadhandling.pipe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;
import pl.kubas.threadhandling.tools.CustomTimer;

public class PipeActivity extends AppCompatActivity {
    public static final String TAG = "PipeActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.output_cipher)
    TextView outputText;

    @BindView(R.id.input_text)
    TextInputEditText inputEditText;

    @BindView(R.id.timer_display)
    TextView timerDisplay;

    private PipedReader reader;
    private PipedWriter writer;
    private Thread workerThread;
    private CustomTimer timer;
    private Handler uiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe);
        ButterKnife.bind(this);
        initToolbar();
        initPipeComponents();
        initInputTextChangeListener();
        initWorkerThread();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        workerThread.interrupt(); //Interrupt thread, stop processing
        try {   //Closing pipe components
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onDestroy();
        }

        return super.onOptionsItemSelected(item);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initWorkerThread() {
        uiThread = new Handler(Looper.getMainLooper());
        workerThread = new Thread(new TextChangeHandler(uiThread, reader, outputText, timer, timerDisplay));
        workerThread.start();
    }


    private void initPipeComponents() {
        timer = new CustomTimer();
        writer = new PipedWriter();
        reader = new PipedReader();
        try {
            writer.connect(reader);   //Connect writer with reader
        } catch (IOException e) {
            Log.e(PipeActivity.TAG, Arrays.toString(e.getStackTrace()));
        }
    }

    private void initInputTextChangeListener() {
        this.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) { //Get last entered char and process by writer
                if (count > before) {
                    try {
                        char lastChar = charSequence.toString().charAt(charSequence.length() - 1);
                        timer.startCounting();
                        writer.write(lastChar);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
