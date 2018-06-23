package pl.kubas.threadhandling.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;

public class MainActivity extends AppCompatActivity {
    private final String REPOSITORY_URL = "https://github.com/RQbas/Android-Thread-Handling";


    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.handling_types_list)
    protected RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initRecyclerView(MenuService.getMenuButtonList(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_internet) {
            Intent openBrowser = new Intent(Intent.ACTION_VIEW);
            openBrowser.setData(Uri.parse(REPOSITORY_URL));
            startActivity(openBrowser);
            return true;
        }

        if (id == R.id.action_info) {
            Toast.makeText(this, "INFORMATION", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(List<MenuButton> buttonList) {
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MenuAdapter menuAdapter = new MenuAdapter(this, buttonList);
        menuRecyclerView.setAdapter(menuAdapter);

    }
}
