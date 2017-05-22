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
 *          1.  2017年5月21日09:23:25
 *              完成了基本的功能
 *          2.  2017年5月22日09:25:57
 *              修改了部分单词拼写错误
 *              对不同位置的Dialog进行设置不同的动画
 *              完成了：API 19（4.4），22（5.1），23（6.0），24（7.0）：虚拟机运行正常
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
        void onItemClick(SimpleDialog simpleDialog,View view,int position);
    }

    /**
     * 重写Dialog
     */
    private final class CustomDialog extends Dialog {

        private ListView listView;
        private ArrayAdapter<String> arrayAdapter;

        /**
         *  初始化：进行Dialog的风格设置等等
         * @param context  上下文
         */
        public CustomDialog(@NonNull Context context) {
            super(context, R.style.SimpleDialog);
            setContentView(R.layout.com_chestnut_dialog_simpledialog_listadapter);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            listView = (ListView) this.findViewById(R.id.listView);
        }

        public CustomDialog addItems(List<String> items) {
            arrayAdapter = new ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    items);
            listView.setAdapter(arrayAdapter);
            return this;
        }

        public void setListener(final OnItemClickListener onItemClickListener) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onItemClickListener.onItemClick(SimpleDialog.this,view,i);
                }
            });
        }

        @Override
        public void show() {
            int width = WindowManager.LayoutParams.WRAP_CONTENT;
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            switch (POSITION_DIALOG) {
                case POSITION_BOTTOM:
                    getWindow().setGravity(Gravity.BOTTOM);
                    getWindow().setWindowAnimations(R.style.SimpleDialog_bottom);
                    width = WindowManager.LayoutParams.MATCH_PARENT;
                    break;
                case POSITION_CENTER:
                    getWindow().setGravity(Gravity.CENTER);
                    getWindow().setWindowAnimations(R.style.SimpleDialog_center);
                    break;
            }
            if (arrayAdapter.getCount()>6) {
                getWindow().setLayout(width, context.getResources().getDisplayMetrics().heightPixels/2);
            }
            else {
                getWindow().setLayout(width, height);
            }
            super.show();
        }
    }
}
