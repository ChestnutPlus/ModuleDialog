package com.chestnut.Dialog.IconSimpleDialog;

import android.support.annotation.IdRes;

/**
 * Created by Chestnut on 2017/5/22.
 */

public class Item {
    public int position;
    public String title;
    public @IdRes int icon;
    @Override
    public String toString() {
        return "Item{" +
                "position=" + position +
                ", title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }
}
