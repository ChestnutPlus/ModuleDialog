package com.chestnut.Dialog.MsgDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chestnut.Dialog.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/5/29 16:38
 *     desc  :  模拟微信的白色对话框，只能设置Msg内容
 *              MD风格。。
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */

public class MsgDialog {

    private CustomDialog customDialog;

    public MsgDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public void show() {
        customDialog.show();
    }

    public void dismiss() {
        customDialog.dismiss();
    }

    public MsgDialog setMsg(String txt) {
        customDialog.setMsg(txt);
        return this;
    }

    public MsgDialog setTitle(String txt) {
        customDialog.setTitle(txt);
        return this;
    }

    public MsgDialog setBtnOk(String txt, OnButtonClickListener buttonClickListener) {
        customDialog.setBtnOk(txt, buttonClickListener);
        return this;
    }

    public MsgDialog setBtnCancel(String txt, OnButtonClickListener buttonClickListener) {
        customDialog.setBtnCancel(txt, buttonClickListener);
        return this;
    }

    public interface OnButtonClickListener {
        void onButtonClick(MsgDialog msgDialog);
    }

    private final class CustomDialog extends Dialog {

        private TextView btnOk;
        private TextView btnCancel;
        private TextView msg;
        private TextView title;

        /**
         *  初始化：进行Dialog的风格设置等等
         * @param context  上下文
         */
        public CustomDialog(@NonNull Context context) {
            super(context, R.style.SimpleDialog);
            setContentView(R.layout.com_chestnut_dialog_msg_dialog);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            getWindow().setGravity(Gravity.CENTER);
            getWindow().setWindowAnimations(R.style.SimpleDialog_center);
            getWindow().setLayout(context.getResources().getDisplayMetrics().widthPixels*9/12, WindowManager.LayoutParams.WRAP_CONTENT);
            btnOk = (TextView) findViewById(R.id.btn_ok);
            btnCancel = (TextView) findViewById(R.id.btn_cancel);
            msg = (TextView) findViewById(R.id.txt_msg);
            title = (TextView) findViewById(R.id.txt_title);
        }

        public void setMsg(String txt) {
            msg.setText(txt);
        }

        public void setTitle(String txt) {
            title.setVisibility(View.VISIBLE);
            title.setText(txt);
        }

        public void setBtnOk(String txt, final OnButtonClickListener buttonClickListener) {
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setText(txt);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonClickListener!=null)
                        buttonClickListener.onButtonClick(MsgDialog.this);
                }
            });
        }

        public void setBtnCancel(String txt, final OnButtonClickListener buttonClickListener) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(txt);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonClickListener!=null)
                        buttonClickListener.onButtonClick(MsgDialog.this);
                }
            });
        }
    }
}
