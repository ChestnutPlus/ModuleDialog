package com.chestnut.Dialog.IconSimpleDialog;

import android.support.annotation.DrawableRes;

/**
 * Created by Chestnut on 2017/5/22.
 */

public class Item {
    public Item(String title,@DrawableRes int icon) {
        this.title = title;
        this.icon = icon;
    }
    public int position;
    public String title;
    public int icon;
    @Override
    public String toString() {
        return "Item{" +
                "position=" + position +
                ", title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }
}
