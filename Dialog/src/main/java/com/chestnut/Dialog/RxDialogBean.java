package com.chestnut.Dialog;

import android.app.Dialog;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/23 14:08
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 *          1.  2017年6月25日18:38:44  增加IntDef限制RxDialog的状态
 * </pre>
 */
public class RxDialogBean {

    public static final int RX_USER_CLICK_OK = -1;
    public static final int RX_USER_CLICK_CANCEL = -2;
    @IntDef({RX_USER_CLICK_OK,RX_USER_CLICK_CANCEL})
    @Retention(RetentionPolicy.SOURCE)
    private @interface RxDialogType {}

    public @RxDialogType int rxStatus;
    public Dialog dialog;

    public RxDialogBean(@RxDialogType int rxStatus, Dialog dialog) {
        this.rxStatus = rxStatus;
        this.dialog = dialog;
    }
}
