package pl.kubas.threadhandling.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.handling_types_list)
    protected RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView(MenuService.getMenuButtonList(this));

    }

    private void initRecyclerView(List<MenuButton> buttonList) {
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MenuAdapter menuAdapter = new MenuAdapter(this, buttonList);
        menuRecyclerView.setAdapter(menuAdapter);

    }
}
