package com.chestnut.Dialog;

import android.app.Dialog;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/23 14:08
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class RxDialogBean {
    public static final int RX_USER_CLICK_OK = -1;
    public static final int RX_USER_CLICK_CANCEL = -2;

    public int rxStatus;
    public Dialog dialog;

    public RxDialogBean(int rxStatus, Dialog dialog) {
        this.rxStatus = rxStatus;
        this.dialog = dialog;
    }
}
