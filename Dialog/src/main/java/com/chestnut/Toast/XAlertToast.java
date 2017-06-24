package com.chestnut.Toast;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chestnut.Dialog.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/24 15:44
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */

public class XAlertToast {

    /*常量*/
    public static final int TYPE_WARNING = -1;
    public static final int TYPE_ERROR = -2;
    public static final int TYPE_SUCCESS = -3;
    @IntDef({TYPE_WARNING,TYPE_ERROR,TYPE_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ALERT_TYPE {}

    /*变量*/
    private Toast toast;
    private TextView textView;
    private ImageView imageView;
    private int type;

    public XAlertToast(Context context) {
        this(context,TYPE_WARNING);
    }

    public XAlertToast(Context context,@ALERT_TYPE int ALERT_TYPE) {
        type = ALERT_TYPE;
        toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.com_chestnut_dialog_xalert_toast,null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        imageView = (ImageView) view.findViewById(R.id.img);
        textView = (TextView) view.findViewById(R.id.txt_msg);
    }

    private void animationSuccess() {
        RotateAnimation rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
    }

    private void animationError() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.5f,1.0f,1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(250);
        imageView.startAnimation(scaleAnimation);
    }

    /**
     * 展示Toast
     * @param msg msg
     */
    public void show(String msg) {
        if (msg==null || msg.length()<=0)
            textView.setVisibility(View.GONE);
        else
            textView.setText(msg);
        switch (type) {
            case TYPE_ERROR:
                animationError();
                imageView.setImageResource(R.drawable.xdialog_error);
                break;
            case TYPE_SUCCESS:
                imageView.setImageResource(R.drawable.xdialog_success);
                animationSuccess();
                break;
            case TYPE_WARNING:
                imageView.setImageResource(R.drawable.xdialog_warning);
                break;
        }
        toast.show();
    }
}
