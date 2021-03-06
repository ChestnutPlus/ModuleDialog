package com.chestnut.Dialog.SimpleDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chestnut.Dialog.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/5/21 15:42
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 *          2017年5月21日09:23:25  ——栗子
 *              完成了基本的功能
 *          2017年5月22日09:25:57  ——栗子
 *              1）修改了部分单词拼写错误
 *              2）对不同位置的Dialog进行设置不同的动画
 *              3）完成了：API 19（4.4），22（5.1），23（6.0），24（7.0）：虚拟机运行正常
 *          2017年5月24日15:33:49  ——栗子
 *              1）对OnItem回调进行扩展，增加返回str
 *              2）增加了点击效果，确保不受app/theme影响
 *          2017年6月23日10:41:39  ——栗子
 *              1）修改了内部类的访问权限
 *              2）干掉编译器的所有黄色Warning
 *
 * </pre>
 */

public class SimpleDialog {

    public static final int POSITION_BOTTOM = -1;
    public static final int POSITION_CENTER = -2;
    @IntDef({POSITION_BOTTOM,POSITION_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    private @interface POSITION_STYLE {}

    private CustomDialog customDialog;
    private int POSITION_DIALOG = POSITION_CENTER;
    private Context context;

    /**
     * 构造方法1：默认为 POSITION_CENTER
     * @param context 传入上下文
     */
    public SimpleDialog(Context context) {
        this.context = context;
        customDialog = new CustomDialog(context);
    }

    /**
     * 构造方法2：传入 Position
     * @param context  上下文
     * @param positionDialog 位置
     */
    public SimpleDialog(Context context,@POSITION_STYLE int positionDialog) {
        this(context);
        this.POSITION_DIALOG = positionDialog;
    }

    public SimpleDialog setStyle(@POSITION_STYLE int positionDialog) {
        this.POSITION_DIALOG = positionDialog;
        return this;
    }

    public SimpleDialog setItems(List<String> items) {
        customDialog.addItems(items);
        return this;
    }

    public SimpleDialog show() {
        customDialog.show();
        return this;
    }

    public SimpleDialog dismiss() {
        customDialog.dismiss();
        return this;
    }

    public SimpleDialog setOnItemClick(OnItemClickListener onItemClickListener) {
        customDialog.setListener(onItemClickListener);
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(SimpleDialog simpleDialog,View view,int position, String string);
    }

    /**
     * 重写Dialog
     */
    private final class CustomDialog extends Dialog {

        private ListView listView;
        private ArrayAdapter<String> arrayAdapter;

        /**
         * 初始化：进行Dialog的风格设置等等
         *
         * @param context 上下文
         */
        CustomDialog(@NonNull Context context) {
            super(context, R.style.SimpleDialog);
            setContentView(R.layout.com_chestnut_dialog_simpledialog_listadapter);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            listView = (ListView) this.findViewById(R.id.listView);
        }

        CustomDialog addItems(List<String> items) {
            arrayAdapter = new ArrayAdapter<>(
                    context,
                    R.layout.com_chestnut_dialog_simpledialog_item,
                    android.R.id.text1,
                    items);
            listView.setAdapter(arrayAdapter);
            return this;
        }

        void setListener(final OnItemClickListener onItemClickListener) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onItemClickListener.onItemClick(SimpleDialog.this, view, i, arrayAdapter.getItem(i));
                }
            });
        }

        @Override
        public void show() {
            int width = WindowManager.LayoutParams.WRAP_CONTENT;
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            switch (POSITION_DIALOG) {
                case POSITION_BOTTOM:
                    if (getWindow() != null) {
                        getWindow().setGravity(Gravity.BOTTOM);
                        getWindow().setWindowAnimations(R.style.SimpleDialog_bottom);
                        width = WindowManager.LayoutParams.MATCH_PARENT;
                    }
                    break;
                case POSITION_CENTER:
                    if (getWindow() != null) {
                        getWindow().setGravity(Gravity.CENTER);
                        getWindow().setWindowAnimations(R.style.SimpleDialog_center);
                        width = context.getResources().getDisplayMetrics().widthPixels * 9 / 12;
                    }
                    break;
            }
            if (arrayAdapter.getCount() > 6) {
                if (getWindow() != null) {
                    getWindow().setLayout(width, context.getResources().getDisplayMetrics().heightPixels * 5 / 12);
                }
            } else {
                if (getWindow() != null) {
                    getWindow().setLayout(width, height);
                }
            }
            super.show();
        }
    }
}

