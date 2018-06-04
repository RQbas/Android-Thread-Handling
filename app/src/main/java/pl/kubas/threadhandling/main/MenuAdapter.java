package pl.kubas.threadhandling.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kubas.threadhandling.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private final List<MenuButton> menuButtonList;
    private Activity activity;


    public MenuAdapter(Activity activity, List<MenuButton> menuButtonList) {
        this.menuButtonList = menuButtonList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_main, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        final MenuButton menuButton = menuButtonList.get(position);
        Drawable loadedIcon;
        try {
            loadedIcon = ContextCompat.getDrawable(activity, menuButton.getIconID());
        } catch (Exception ex) {
            loadedIcon = ContextCompat.getDrawable(activity, R.drawable.ic_error);
        }

        holder.title.setText(getString(menuButton.getTitleID()));
        holder.description.setText(getString(menuButton.getDescriptionID()));
        holder.icon.setImageDrawable(loadedIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, menuButton.getTargetActivity());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuButtonList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thread_type_title)
        TextView title;

        @BindView(R.id.thread_type_desc)
        TextView description;

        @BindView(R.id.thread_type_icon)
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    private String getString(int ID) {
        return activity.getString(ID);
    }

}
