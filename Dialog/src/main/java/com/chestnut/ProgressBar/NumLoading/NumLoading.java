package com.chestnut.ProgressBar.NumLoading;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chestnut.Dialog.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/5/25 10:11
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class NumLoading extends Dialog{

    private TextView progressTxt;
    private TextView titleTxt;
    private ProgressBar progressBar;

    public NumLoading(@NonNull Context context) {
        super(context, R.style.SimpleDialog);
        setContentView(R.layout.com_chestnut_dialog_progressbar_num_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.SimpleDialog_center);
        getWindow().setGravity(Gravity.CENTER);
        titleTxt = (TextView) findViewById(R.id.title);
        progressTxt = (TextView) findViewById(R.id.progress);
        progressBar = (ProgressBar) findViewById(R.id.progress_loading);
    }

    @Override
    public void dismiss() {
        if (this.isShowing())
            super.dismiss();
    }

    public NumLoading setProgressVisibility(boolean isVisibility) {
        this.progressTxt.setVisibility(isVisibility? View.VISIBLE:View.GONE);
        return this;
    }

    public NumLoading setTitle(String title) {
        this.titleTxt.setText(title);
        return this;
    }

    public NumLoading setTitleVisibility(boolean isVisibility) {
        this.titleTxt.setVisibility(isVisibility? View.VISIBLE:View.GONE);
        return this;
    }

    public NumLoading setProgress(int progress) {
        this.progressTxt.setText(progress+"%");
        if (progress<0)
            this.progressBar.setProgress(0);
        else if (progress>100)
            this.progressBar.setProgress(100);
        else
            this.progressBar.setProgress(progress);
        return this;
    }
}
