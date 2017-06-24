package com.chestnut.Dialog.MsgDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chestnut.Dialog.OnBtnClickListener;
import com.chestnut.Dialog.R;
import com.chestnut.Dialog.RxDialogBean;
import com.chestnut.Dialog.XDialog;

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
 *          2017年6月23日14:51:40
 *              1.  增加API
 *              2.  定义统一的接口。
 * </pre>
 */

public class MsgDialog implements XDialog<MsgDialog>{

    private CustomDialog customDialog;

    public MsgDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public Observable<RxDialogBean> rxShow() {
        return Observable.create(new Observable.OnSubscribe<RxDialogBean>() {
            @Override
            public void call(final Subscriber<? super RxDialogBean> subscriber) {
                setBtnOkListener(new OnBtnClickListener() {
                    @Override
                    public void onButtonClick(Dialog dialog) {
                        subscriber.onNext(new RxDialogBean(RxDialogBean.RX_USER_CLICK_OK,dialog));
                    }
                });
                setBtnCancelListener(new OnBtnClickListener() {
                    @Override
                    public void onButtonClick(Dialog dialog) {
                        subscriber.onNext(new RxDialogBean(RxDialogBean.RX_USER_CLICK_CANCEL,dialog));
                    }
                });
                show();
            }
        });
    }

    @Override
    public void show() {
        customDialog.show();
    }

    @Override
    public void dismiss() {
        customDialog.dismiss();
    }

    public MsgDialog setMsg(String txt) {
        customDialog.msg.setText(txt);
        return this;
    }

    @Override
    public MsgDialog setBtnOkTxt(String txt) {
        customDialog.btnOk.setVisibility(View.VISIBLE);
        customDialog.btnOk.setText(txt);
        return this;
    }

    @Override
    public MsgDialog setBtnCancelTxt(String txt) {
        customDialog.btnCancel.setVisibility(View.VISIBLE);
        customDialog.btnCancel.setText(txt);
        return this;
    }

    @Override
    public MsgDialog setCancelable(boolean cancelable) {
        customDialog.setCancelable(cancelable);
        return this;
    }

    @Override
    public MsgDialog setCanceledOnTouchOutside(boolean cancelable) {
        customDialog.setCanceledOnTouchOutside(true);
        return this;
    }

    @Override
    public MsgDialog setBtnOkListener(final OnBtnClickListener onBtnClickListener) {
        if (onBtnClickListener!=null) {
            customDialog.btnOk.setVisibility(View.VISIBLE);
            customDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBtnClickListener.onButtonClick(customDialog);
                }
            });
        }
        return this;
    }

    @Override
    public MsgDialog setBtnCancelListener(final OnBtnClickListener onBtnClickListener) {
        if (onBtnClickListener!=null) {
            customDialog.btnCancel.setVisibility(View.VISIBLE);
            customDialog.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBtnClickListener.onButtonClick(customDialog);
                }
            });
        }
        return this;
    }

    @Override
    public MsgDialog setTitle(String txt) {
        customDialog.title.setVisibility(View.VISIBLE);
        customDialog.title.setText(txt);
        return this;
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
            if (getWindow()!=null) {
                getWindow().setGravity(Gravity.CENTER);
                getWindow().setWindowAnimations(R.style.SimpleDialog_center);
                getWindow().setLayout(context.getResources().getDisplayMetrics().widthPixels * 9 / 12, WindowManager.LayoutParams.WRAP_CONTENT);
            }
            btnOk = (TextView) findViewById(R.id.btn_ok);
            btnCancel = (TextView) findViewById(R.id.btn_cancel);
            msg = (TextView) findViewById(R.id.txt_msg);
            title = (TextView) findViewById(R.id.txt_title);
        }
    }
}
