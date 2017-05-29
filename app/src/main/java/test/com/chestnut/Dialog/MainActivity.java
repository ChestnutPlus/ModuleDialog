package test.com.chestnut.Dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chestnut.Common.ui.Toastc;
import com.chestnut.Dialog.IconSimpleDialog.IconSimpleDialog;
import com.chestnut.Dialog.IconSimpleDialog.Item;
import com.chestnut.Dialog.MsgDialog.MsgDialog;
import com.chestnut.Dialog.SimpleDialog.SimpleDialog;
import com.chestnut.ProgressBar.LoadingBar.Loading;
import com.chestnut.ProgressBar.NumLoading.NumLoading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Toastc toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = new Toastc(this, Toast.LENGTH_LONG);
        numLoading = new NumLoading(MainActivity.this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> items = new ArrayList<>();
                items.add(new Item("微信",R.drawable.a));
                items.add(new Item("微信朋友圈",R.drawable.b));
                items.add(new Item("微博",R.drawable.c));
                items.add(new Item("博客",R.drawable.d));
                items.add(new Item("蓝牙",R.drawable.a));
                items.add(new Item("QQ好友",R.drawable.b));
                items.add(new Item("QQ空间",R.drawable.c));
                new IconSimpleDialog(MainActivity.this,IconSimpleDialog.POSITION_CENTER)
                        .setItems(items)
                        .setTitleVisibility(false)
                        .setTitle("王尼玛测试")
                        .setOnItemClick(new IconSimpleDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(IconSimpleDialog simpleDialog, View view, int position, Item item) {
                                toast.setText(item.title).show();
                                if (position==2) {
                                    simpleDialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> items = new ArrayList<>();
                items.add(new Item("微信",R.mipmap.ic_launcher));
                items.add(new Item("微信朋友圈",R.mipmap.ic_launcher));
                items.add(new Item("微博",R.mipmap.ic_launcher));
                items.add(new Item("博客",R.mipmap.ic_launcher));
                items.add(new Item("蓝牙",R.mipmap.ic_launcher));
                items.add(new Item("QQ好友",R.mipmap.ic_launcher));
                items.add(new Item("QQ空间",R.mipmap.ic_launcher));
                new IconSimpleDialog(MainActivity.this,IconSimpleDialog.POSITION_BOTTOM)
                        .setNumColumns(4)
                        .setItems(items)
                        .setTitleVisibility(true)
                        .setTitle("王尼玛测试")
                        .setOnItemClick(new IconSimpleDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(IconSimpleDialog simpleDialog, View view, int position, Item item) {
                                toast.setText(item.title).show();
                                if (position==2) {
                                    simpleDialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> strings = new ArrayList<String>();
                for (int i=0; i<10; i++) {
                    strings.add("i:"+i);
                }
                new SimpleDialog(MainActivity.this,SimpleDialog.POSITION_CENTER)
                        .setItems(strings)
                        .setOnItemClick(new SimpleDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(SimpleDialog simpleDialog, View view, int position, String s) {
                                toast.setText(s).show();
                                if (position==2) {
                                    simpleDialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> strings = new ArrayList<String>();
                for (int i=0; i<10; i++) {
                    strings.add("i:"+i);
                }
                new SimpleDialog(MainActivity.this,SimpleDialog.POSITION_BOTTOM)
                        .setItems(strings)
                        .setOnItemClick(new SimpleDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(SimpleDialog simpleDialog, View view, int position, String s) {
                                toast.setText(s).show();
                                if (position==2) {
                                    simpleDialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Loading(MainActivity.this)
                        .show();
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numLoading.setTitle("王尼玛：")
                        .setTitleVisibility(true)
                        .setProgressVisibility(true)
                        .show();
                Observable.interval(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                numLoading.setProgress(aLong.intValue());
                            }
                        });
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MsgDialog(MainActivity.this)
                        .setMsg("这是一条简单的信息")
                        .setBtnCancel("cancel", new MsgDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(MsgDialog msgDialog) {
                                toast.setText("cancel").show();
                                msgDialog.dismiss();
                            }
                        })
                        .setBtnOk("ok", new MsgDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(MsgDialog msgDialog) {
                                toast.setText("ok").show();
                                msgDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private NumLoading numLoading;
}
