package com.chestnut.Dialog;

import rx.Observable;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/23 13:40
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public interface XDialog<T> {
    void show();
    Observable<RxDialogBean> rxShow();
    void dismiss();

    T setTitle(String title);
    T setMsg(String msg);
    T setBtnOkTxt(String txt);
    T setBtnCancelTxt(String txt);
    T setCancelable(boolean cancelable);
    T setCanceledOnTouchOutside(boolean cancelable);
    T setBtnOkListener(OnBtnClickListener onBtnClickListener);
    T setBtnCancelListener(OnBtnClickListener onBtnClickListener);
}


