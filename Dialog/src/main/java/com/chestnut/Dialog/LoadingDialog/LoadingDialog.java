package com.chestnut.Dialog.LoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chestnut.Common.utils.ConvertUtils;
import com.chestnut.Dialog.R;
import com.chestnut.Dialog.RxDialogBean;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    private Subscription overTimeSubscription;

    public LoadingDialog(Context context) {
        this.context = context;
        customDialog = new CustomDialog(context);
    }

    public LoadingDialog setCancelable(boolean cancelable) {
        customDialog.setCancelable(cancelable);
        return this;
    }

    public LoadingDialog setCanceledOnTouchOutside(boolean cancelable) {
        customDialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 展示Loading
     * @param msg   msg
     * @param overTimeSeconds   超时时间
     */
    public void show(String msg,int overTimeSeconds) {
        if (msg!=null && msg.length()>0) {
            customDialog.msg.setVisibility(View.VISIBLE);
            customDialog.msg.setText(msg);
        }
        else {
            customDialog.msg.setVisibility(View.GONE);
            customDialog.msg.setText("");
        }
        customDialog.show();
        if (overTimeSeconds>0) {
            overTimeSubscription = Observable.just(customDialog)
                    .delay(overTimeSeconds, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<CustomDialog>() {
                        @Override
                        public void call(CustomDialog customDialog) {
                            customDialog.dismiss();
                        }
                    });
        }
    }
    public void show(String msg) {
        show(msg,0);
    }
    public void show() {
        show(null,0);
    }
    public void show(int overTimeSeconds) {
        show(null,overTimeSeconds);
    }

    public Observable<RxDialogBean> rxShow() {
        return rxShow(null,0);
    }
    public Observable<RxDialogBean> rxShow(final String msg, final int overTimeSeconds) {
        return Observable.create(new Observable.OnSubscribe<RxDialogBean>() {
            @Override
            public void call(final Subscriber<? super RxDialogBean> subscriber) {
                customDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        subscriber.onNext(new RxDialogBean(RxDialogBean.RX_USER_CLICK_CANCEL,customDialog));
                        subscriber.onCompleted();
                    }
                });
                customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        subscriber.onNext(new RxDialogBean(RxDialogBean.RX_USER_CLICK_CANCEL,customDialog));
                        subscriber.onCompleted();
                    }
                });
                show(msg,overTimeSeconds);
            }
        });
    }
    public Observable<RxDialogBean> rxShow(final String msg) {
        return rxShow(msg,0);
    }
    public Observable<RxDialogBean> rxShow(int overTimeSecondes) {
        return rxShow(null,overTimeSecondes);
    }

    public void dismiss() {
        if (overTimeSubscription!=null && !overTimeSubscription.isUnsubscribed()) {
            overTimeSubscription.unsubscribe();
            overTimeSubscription = null;
        }
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
