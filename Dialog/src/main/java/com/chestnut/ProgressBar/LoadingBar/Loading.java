package com.chestnut.ProgressBar.LoadingBar;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.chestnut.Dialog.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/5/24 22:54
 *     desc  :  完成了一个简单的loading，不带文字
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */

public class Loading extends Dialog{

    public Loading(@NonNull Context context) {
        super(context, R.style.SimpleDialog);
        setContentView(R.layout.com_chestnut_dialog_progressbar_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.SimpleDialog_center);
        getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void dismiss() {
        if (this.isShowing())
            super.dismiss();
    }
}
