package com.chestnut.Dialog.XAlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chestnut.Dialog.OnBtnClickListener;
import com.chestnut.Dialog.R;
import com.chestnut.Dialog.RxDialogBean;
import com.chestnut.Dialog.XDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import rx.Observable;
import rx.Subscriber;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/23 10:20
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class XAlertDialog implements XDialog<XAlertDialog>{

    private boolean OpenLog = true;
    private String TAG = "XAlertDialog";

    /*常量*/
    public static final int TYPE_WARNING = -1;
    public static final int TYPE_ERROR = -2;
    public static final int TYPE_SUCCESS = -3;
    public static final int TYPE_DEFAULT = -4;
    @IntDef({TYPE_WARNING,TYPE_ERROR,TYPE_SUCCESS,TYPE_DEFAULT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ALERT_TYPE {}

    /*变量*/
    private CustomDialog customDialog;
    private @ALERT_TYPE int ALERT_TYPE = TYPE_DEFAULT;
    private Context context;

    /*方法*/
    public XAlertDialog(@NonNull Context context) {
        this(context,TYPE_DEFAULT);
    }

    public XAlertDialog(@NonNull Context context,@ALERT_TYPE int alertType) {
        customDialog = new CustomDialog(context);
        ALERT_TYPE = alertType;
        this.context = context;
        if (customDialog.getWindow()!=null) {
            customDialog.getWindow().setWindowAnimations(R.style.XAlertDialog);
        }
    }

    @Override
    public void show() {
        customDialog.show();
    }

    @Override
    public Observable<RxDialogBean> rxShow() {
        return Observable.create(new Observable.OnSubscribe<RxDialogBean>() {
            @Override
            public void call(final Subscriber<? super RxDialogBean> subscriber) {
                setBtnCancelListener(new OnBtnClickListener() {
                    @Override
                    public void onButtonClick(Dialog dialog) {
                        subscriber.onNext(new RxDialogBean(RxDialogBean.RX_USER_CLICK_CANCEL,dialog));
                    }
                });
                setBtnOkListener(new OnBtnClickListener() {
                    @Override
                    public void onButtonClick(Dialog dialog) {
                        subscriber.onNext(new RxDialogBean(RxDialogBean.RX_USER_CLICK_OK,dialog));
                    }
                });
                show();
            }
        });
    }

    @Override
    public void dismiss() {
        customDialog.dismiss();
    }

    @Override
    public XAlertDialog setTitle(String title) {
        customDialog.title.setVisibility(View.VISIBLE);
        customDialog.title.setText(title);
        return this;
    }

    @Override
    public XAlertDialog setMsg(String msg) {
        customDialog.msg.setVisibility(View.VISIBLE);
        customDialog.msg.setText(msg);
        return this;
    }

    @Override
    public XAlertDialog setBtnOkTxt(String txt) {
        customDialog.btnOk.setVisibility(View.VISIBLE);
        customDialog.btnOk.setText(txt);
        return this;
    }

    @Override
    public XAlertDialog setBtnCancelTxt(String txt) {
        customDialog.btnCancel.setVisibility(View.VISIBLE);
        customDialog.btnCancel.setText(txt);
        return this;
    }

    @Override
    public XAlertDialog setBtnOkListener(final OnBtnClickListener onBtnClickListener) {
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
    public XAlertDialog setBtnCancelListener(final OnBtnClickListener onBtnClickListener) {
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

    private void animationError() {
        customDialog.imageView.setImageResource(R.drawable.xdialog_error);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,2.0f,1.0f,1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(250);
        customDialog.imageView.startAnimation(scaleAnimation);
    }

    private void animationSuccess() {
        customDialog.imageView.setImageResource(R.drawable.xdialog_success);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        customDialog.imageView.startAnimation(rotateAnimation);
    }

    private void animationWarning() {
        customDialog.imageView.setImageResource(R.drawable.xdialog_warning);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,-1.0f,
                Animation.RELATIVE_TO_SELF,0.0f,
                Animation.RELATIVE_TO_SELF,0.0f,
                Animation.RELATIVE_TO_SELF,0.0f
        );
        translateAnimation.setDuration(500);
        customDialog.imageView.startAnimation(translateAnimation);
    }

    /*类，接口*/
    private final class CustomDialog extends Dialog {
        ImageView imageView;
        TextView title;
        TextView msg;
        TextView btnOk;
        TextView btnCancel;
        CustomDialog(@NonNull Context context) {
            super(context, R.style.SimpleDialog);
            setContentView(R.layout.com_chestnut_dialog_xalertdialog);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            if (getWindow()!=null) {
                getWindow().setGravity(Gravity.CENTER);
                getWindow().setWindowAnimations(R.style.SimpleDialog_center);
                getWindow().setLayout(context.getResources().getDisplayMetrics().widthPixels * 9 / 12, WindowManager.LayoutParams.WRAP_CONTENT);
            }
            imageView = (ImageView) findViewById(R.id.img_custom);
            title = (TextView) findViewById(R.id.txt_title);
            msg = (TextView) findViewById(R.id.txt_content);
            btnOk = (TextView) findViewById(R.id.txt_ok);
            btnCancel = (TextView) findViewById(R.id.txt_cancel);
        }

        @Override
        protected void onStart() {
            //layout Animation
            if (getWindow()!=null) {
                AnimationSet animationSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.xdialog_in);
                getWindow().getDecorView().findViewById(android.R.id.content).startAnimation(animationSet);
            }
            //img Animation
            switch (ALERT_TYPE) {
                case TYPE_DEFAULT:
                    customDialog.imageView.setVisibility(View.GONE);
                    break;
                case TYPE_WARNING:
                    animationWarning();
                    break;
                case TYPE_ERROR:
                    animationError();
                    break;
                case TYPE_SUCCESS:
                    animationSuccess();
                    break;
            }
        }
    }
}
