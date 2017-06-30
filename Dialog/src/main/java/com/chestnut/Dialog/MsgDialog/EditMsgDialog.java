package com.chestnut.Dialog.MsgDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chestnut.Dialog.R;
import com.chestnut.Dialog.RxDialogBean;

import rx.Observable;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/30 15:32
 *     desc  :  带有输入框的Dialog，风格是微信风格。
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class EditMsgDialog{

    private CustomDialog customDialog;
    private String[] contentTxt={"","",""};
    private int editNum = 0;

    public EditMsgDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public void show() {
        customDialog.show();
    }

    public Observable<RxDialogBean> rxShow() {
        return null;
    }

    public void dismiss() {
        customDialog.dismiss();
    }

    public EditMsgDialog setTitle(String title) {
        customDialog.title.setText(title);
        return this;
    }

    public EditMsgDialog setBtnOkTxt(String txt) {
        customDialog.btnOk.setVisibility(View.VISIBLE);
        customDialog.btnOk.setText(txt);
        return this;
    }

    public EditMsgDialog setBtnCancelTxt(String txt) {
        customDialog.btnCancel.setVisibility(View.VISIBLE);
        customDialog.btnCancel.setText(txt);
        return this;
    }

    public EditMsgDialog setCancelable(boolean cancelable) {
        customDialog.setCancelable(cancelable);
        return this;
    }

    public EditMsgDialog setCanceledOnTouchOutside(boolean cancelable) {
        customDialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public EditMsgDialog setBtnOkListener(final OnBtnClickListener onBtnClickListener) {
        if (onBtnClickListener!=null) {
            customDialog.btnOk.setVisibility(View.VISIBLE);
            customDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBtnClickListener.onButtonClick(customDialog,contentTxt);
                }
            });
        }
        return this;
    }

    public EditMsgDialog setBtnCancelListener(final OnBtnClickListener onBtnClickListener) {
        if (onBtnClickListener!=null) {
            customDialog.btnCancel.setVisibility(View.VISIBLE);
            customDialog.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBtnClickListener.onButtonClick(customDialog,contentTxt);
                }
            });
        }
        return this;
    }

    public EditMsgDialog addOneInput(String hintTxt) {
        if (editNum<=2) {
            customDialog.edit[editNum].setVisibility(View.VISIBLE);
            customDialog.edit[editNum].setHint(hintTxt);
            editNum++;
        }
        return this;
    }

    private class CustomDialog extends Dialog {
        TextView btnOk;
        TextView btnCancel;
        TextView title;
        EditText[] edit = {null,null,null};
        CustomDialog(@NonNull Context context) {
            super(context, R.style.SimpleDialog);
            setContentView(R.layout.com_chestnut_dialog_edit_msg_dialog);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            if (getWindow()!= null) {
                getWindow().setGravity(Gravity.CENTER);
                getWindow().setWindowAnimations(R.style.SimpleDialog_center);
                getWindow().setLayout(context.getResources().getDisplayMetrics().widthPixels * 9 / 12, WindowManager.LayoutParams.WRAP_CONTENT);
            }
            btnOk = (TextView) findViewById(R.id.btn_ok);
            btnCancel = (TextView) findViewById(R.id.btn_cancel);
            title = (TextView) findViewById(R.id.txt_title);
            edit[0] = (EditText) findViewById(R.id.edit1);
            edit[1] = (EditText) findViewById(R.id.edit2);
            edit[2] = (EditText) findViewById(R.id.edit3);
            for (int i = 0; i < 3; i++) {
                final int a = i;
                edit[i].addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        contentTxt[a] = editable.toString();
                    }
                });
            }
        }
    }

    public interface OnBtnClickListener {
        void onButtonClick(Dialog dialog,String[] editTxt);
    }
}
