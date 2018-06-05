package pl.kubas.threadhandling.main;

import android.support.v7.app.AppCompatActivity;

public class MenuButton {
    private int titleID;
    private int descriptionID;
    private int iconID;
    private Class<? extends AppCompatActivity> targetActivity;


    MenuButton(int titleID, int descriptionID, int iconID, Class<? extends AppCompatActivity> activity) {
        this.iconID = iconID;
        this.targetActivity = activity;
        this.titleID = titleID;
        this.descriptionID = descriptionID;
    }

    public int getTitleID() {
        return titleID;
    }

    public int getDescriptionID() {
        return descriptionID;
    }

    public int getIconID() {
        return iconID;
    }

    public Class<? extends AppCompatActivity> getTargetActivity() {
        return targetActivity;
    }
}
