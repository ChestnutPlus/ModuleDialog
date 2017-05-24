package com.chestnut.Dialog.IconSimpleDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chestnut.Dialog.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/5/22 15:42
 *     desc  :
 *          带有Icon的MD对话框。
 *     thanks To:
 *     dependent on:
 *     update log:
 *          1.  2017年5月23日14:43:10 完成第一版
 *          2.  2017年5月24日14:43:21
 *              1）增加了点击效果，确保不受app/theme影响
 *              2）增加了设置行数的API
 *              3）测试通过：4.4，5.1，6.0，7.0
 * </pre>
 */

public class IconSimpleDialog {

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
    public IconSimpleDialog(Context context) {
        this.context = context;
        customDialog = new CustomDialog(context);
    }

    /**
     * 构造方法2：传入 Position
     * @param context  上下文
     * @param positionDialog 位置
     */
    public IconSimpleDialog(Context context,@POSITION_STYLE int positionDialog) {
        this(context);
        this.POSITION_DIALOG = positionDialog;
    }

    public IconSimpleDialog setItems(List<Item> items) {
        customDialog.setItems(items);
        return this;
    }

    public IconSimpleDialog setTitle(String title) {
        customDialog.setTitle(title);
        return this;
    }

    public IconSimpleDialog setNumColumns(int numColumns) {
        customDialog.setNumColumns(numColumns);
        return this;
    }

    public IconSimpleDialog setTitleVisibility(boolean isVisibility) {
        customDialog.setTitleVisibility(isVisibility);
        return this;
    }

    public IconSimpleDialog show() {
        customDialog.show();
        return this;
    }

    public IconSimpleDialog dismiss() {
        customDialog.dismiss();
        return this;
    }

    public IconSimpleDialog setOnItemClick(OnItemClickListener onItemClickListener) {
        customDialog.setListener(onItemClickListener);
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(IconSimpleDialog simpleDialog, View view, int position,Item item);
    }
    
    /**
     * 重写Dialog
     */
    private final class CustomDialog extends Dialog {

        private SimpleAdapter simpleAdapter;
        private GridView gridView;
        private List<Item> items;
        private TextView titleTxt;

        public CustomDialog(@NonNull Context context) {
            super(context, R.style.SimpleDialog);
            setContentView(R.layout.com_chestnut_dialog_icon_simpledialog_grid_dapter);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            gridView = (GridView) findViewById(R.id.gridView);
            titleTxt = (TextView) findViewById(R.id.title);
        }

        public void setItems(List<Item> items) {
            this.items = items;
            List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
            for (Item i :
                    items) {
                Map<String,Object> temp = new HashMap<>();
                temp.put("imgIcon",i.icon);
                temp.put("title",i.title);
                listItems.add(temp);
            }
            simpleAdapter = new SimpleAdapter(
                    context,
                    listItems,
                    R.layout.com_chestnut_dialog_icon_gridview_item,
                    new String[] {"imgIcon","title"},
                    new int[] {R.id.imageView,R.id.textView});
            gridView.setAdapter(simpleAdapter);
        }

        public void setListener(final IconSimpleDialog.OnItemClickListener onItemClickListener) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onItemClickListener.onItemClick(IconSimpleDialog.this,view,i,items.get(i));
                }
            });
        }

        public void setTitle(String title) {
            this.titleTxt.setText(title);
        }

        public void setTitleVisibility(boolean isVisibility) {
            this.titleTxt.setVisibility(isVisibility?View.VISIBLE:View.GONE);
        }

        public void setNumColumns(int numColumns) {
            gridView.setNumColumns(numColumns);
        }

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
            if (simpleAdapter.getCount()>7) {
                getWindow().setLayout(width, context.getResources().getDisplayMetrics().heightPixels/2);
            }
            else {
                getWindow().setLayout(width, height);
            }
            super.show();
        }
    }
}
