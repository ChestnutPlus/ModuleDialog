package com.chestnut.Dialog.LoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chestnut.Common.utils.ConvertUtils;
import com.chestnut.Dialog.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/24 21:12
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */

public class LoadingDialog {

    private CustomDialog customDialog;
    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
        customDialog = new CustomDialog(context);
    }

    public void show(@NonNull String msg) {
        customDialog.msg.setVisibility(View.VISIBLE);
        customDialog.msg.setText(msg);
        customDialog.show();
    }

    public LoadingDialog setCancelable(boolean cancelable) {
        customDialog.setCancelable(cancelable);
        return this;
    }

    public LoadingDialog setCanceledOnTouchOutside(boolean cancelable) {
        customDialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public void show() {
        customDialog.show();
    }

    public void dismiss() {
        customDialog.dismiss();
    }

    private class CustomDialog extends Dialog {
        TextView msg;
        CustomDialog(@NonNull Context context) {
            super(context, R.style.DialogLoading);
            setContentView(R.layout.com_chestnut_dialog_loading);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            msg = (TextView) findViewById(R.id.txt_msg);
        }

        @Override
        public void show() {
            if (getWindow()!=null) {
                getWindow().setGravity(Gravity.CENTER);
                if (msg.getText().length()<=0)
                    getWindow().setLayout(ConvertUtils.dp2px(context,100), WindowManager.LayoutParams.WRAP_CONTENT);
                else
                    getWindow().setLayout(ConvertUtils.dp2px(context,120), WindowManager.LayoutParams.WRAP_CONTENT);
            }
            super.show();
        }
    }
}
