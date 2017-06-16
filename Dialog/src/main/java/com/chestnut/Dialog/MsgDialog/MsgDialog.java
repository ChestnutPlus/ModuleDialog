package com.chestnut.Dialog.MsgDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chestnut.Dialog.R;

import rx.Observable;
import rx.Subscriber;

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
 *          2017年6月16日22:52:52
 *              1.  增加RxShow(),调整内部API。——栗子
 * </pre>
 */

public class MsgDialog {

    public static final int RX_USER_CLICK_OK = -1;
    public static final int RX_USER_CLICK_CANCEL = -2;
    public static final int RX_USER_CANCEL = -3;

    private CustomDialog customDialog;

    public MsgDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public Observable<RxDialogBean> rxShow() {
        return Observable.create(new Observable.OnSubscribe<RxDialogBean>() {
            @Override
            public void call(final Subscriber<? super RxDialogBean> subscriber) {
                setBtnCancel(null, new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(MsgDialog msgDialog) {
                        subscriber.onNext(new RxDialogBean(RX_USER_CLICK_CANCEL,msgDialog));
                    }
                });
                setBtnOk(null, new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(MsgDialog msgDialog) {
                        subscriber.onNext(new RxDialogBean(RX_USER_CLICK_OK,msgDialog));
                    }
                });
                customDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        subscriber.onNext(new RxDialogBean(RX_USER_CANCEL,MsgDialog.this));
                    }
                });
                show();
            }
        });
    }

    public void show() {
        customDialog.show();
    }

    public void dismiss() {
        customDialog.dismiss();
    }

    public MsgDialog setMsg(String txt) {
        customDialog.msg.setText(txt);
        return this;
    }

    public MsgDialog setTitle(String txt) {
        customDialog.title.setVisibility(View.VISIBLE);
        customDialog.title.setText(txt);
        return this;
    }

    public MsgDialog setBtnOk(String txt,final OnButtonClickListener buttonClickListener) {
        if (txt!=null) {
            customDialog.btnOk.setText(txt);
            customDialog.btnOk.setVisibility(View.VISIBLE);
        }
        if (buttonClickListener!=null)
            customDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClickListener.onButtonClick(MsgDialog.this);
                }
            });
        return this;
    }

    public MsgDialog setBtnCancel(String txt,final OnButtonClickListener buttonClickListener) {
        if (txt!=null) {
            customDialog.btnCancel.setVisibility(View.VISIBLE);
            customDialog.btnCancel.setText(txt);
        }
        if (buttonClickListener!=null)
            customDialog.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClickListener.onButtonClick(MsgDialog.this);
                }
            });
        return this;
    }

    public interface OnButtonClickListener {
        void onButtonClick(MsgDialog msgDialog);
    }

    private class CustomDialog extends Dialog {

        TextView btnOk;
        TextView btnCancel;
        TextView msg;
        TextView title;

        CustomDialog(@NonNull Context context) {
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
    }

    public class RxDialogBean {
        public int RX_TYPE;
        public MsgDialog msgDialog;
        public RxDialogBean(int RX_TYPE, MsgDialog msgDialog) {
            this.RX_TYPE = RX_TYPE;
            this.msgDialog = msgDialog;
        }
    }
}
