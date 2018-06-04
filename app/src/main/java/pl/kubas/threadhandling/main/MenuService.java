package pl.kubas.threadhandling.main;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.kubas.threadhandling.R;
import pl.kubas.threadhandling.pipe.PipeActivity;

public class MenuService {


    public static List<MenuButton> getMenuButtonList(Context context) {
        List<MenuButton> list = new ArrayList<>();
        list.add(new MenuButton(R.string.pipe_title, R.string.pipe_desc, R.drawable.ic_pipe, PipeActivity.class));

        return list;
    }


}
