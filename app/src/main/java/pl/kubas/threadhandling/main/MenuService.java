package pl.kubas.threadhandling.main;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.kubas.threadhandling.R;
import pl.kubas.threadhandling.messages.bidirectional.BidirectionalMessageActivity;
import pl.kubas.threadhandling.messages.unidirectional.MessagePassingActivity;
import pl.kubas.threadhandling.pipe.PipeActivity;

public class MenuService {


    public static List<MenuButton> getMenuButtonList(Context context) {
        List<MenuButton> list = new ArrayList<>();
        list.add(new MenuButton(R.string.pipe_title, R.string.pipe_short_desc, R.drawable.ic_pipe, PipeActivity.class));
        list.add(new MenuButton(R.string.message_passing_title, R.string.message_passing_short_desc, R.drawable.ic_letter, MessagePassingActivity.class));
        list.add(new MenuButton(R.string.bidirectional_passing_title, R.string.bidirectional_passing_short_desc, R.drawable.ic_bidirectional, BidirectionalMessageActivity.class));
        return list;
    }


}
