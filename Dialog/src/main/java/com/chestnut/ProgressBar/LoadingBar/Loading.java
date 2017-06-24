package com.chestnut.ProgressBar.LoadingBar;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chestnut.Dialog.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/5/24 22:54
 *     desc  :  完成了一个简单的loading
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */

public class Loading extends Dialog{

    private TextView textView;

    public Loading(@NonNull Context context) {
        super(context, R.style.SimpleDialog);
        setContentView(R.layout.com_chestnut_dialog_progressbar_loading);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        if (getWindow()!=null) {
            getWindow().setWindowAnimations(R.style.SimpleDialog_center);
            getWindow().setGravity(Gravity.CENTER);
        }
        textView = (TextView) findViewById(R.id.txt_msg);
    }

    @Override
    public void dismiss() {
        if (this.isShowing())
            super.dismiss();
    }

    public void show(String s) {
        if (s==null || s.length()<=0) {
            textView.setVisibility(View.GONE);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(s);
        }
        show();
    }
}
