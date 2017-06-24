package test.com.chestnut.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chestnut.Common.ui.Toastc;
import com.chestnut.Dialog.IconSimpleDialog.IconSimpleDialog;
import com.chestnut.Dialog.IconSimpleDialog.Item;
import com.chestnut.Dialog.LoadingDialog.LoadingDialog;
import com.chestnut.Dialog.MsgDialog.MsgDialog;
import com.chestnut.Dialog.OnBtnClickListener;
import com.chestnut.Dialog.RxDialogBean;
import com.chestnut.Dialog.SimpleDialog.SimpleDialog;
import com.chestnut.Dialog.XAlertDialog.XAlertDialog;
import com.chestnut.ProgressBar.LoadingBar.Loading;
import com.chestnut.ProgressBar.NumLoading.NumLoading;
import com.chestnut.Toast.XAlertToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Toastc toast = null;
    private boolean OpenLog = true;
    private String TAG = "MainActivity";

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
                        .show("正在提交订单...");
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numLoading.setTitle("王尼玛：")
                        .setTitleVisibility(true)
                        .setProgressVisibility(true)
                        .show();
                Observable.interval(200, TimeUnit.MILLISECONDS)
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
                        .setTitle("Title")
                        .setMsg("这是一条简单的信息")
                        .show();
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XAlertDialog(MainActivity.this,XAlertDialog.TYPE_SUCCESS)
                        .setTitle("title")
                        .setBtnOkListener(new OnBtnClickListener() {
                            @Override
                            public void onButtonClick(Dialog dialog) {
                                toast.setText("setBtnOkListener").show();
                            }
                        })
                        .setBtnCancelListener(new OnBtnClickListener() {
                            @Override
                            public void onButtonClick(Dialog dialog) {
                                toast.setText("onButtonClick").show();
                            }
                        })
                        .setMsg("洗洗就睡了空间的风口浪尖势力扩大解放了放假了深刻搭街坊拉萨角度来看分解落实贷款")
                        .rxShow()
                        .subscribe(new Action1<RxDialogBean>() {
                            @Override
                            public void call(RxDialogBean rxDialogBean) {
                                switch (rxDialogBean.rxStatus) {
                                    case RxDialogBean.RX_USER_CLICK_CANCEL:
                                        toast.setText("RX_USER_CLICK_CANCEL").show();
                                        break;
                                    case RxDialogBean.RX_USER_CLICK_OK:
                                        toast.setText("RX_USER_CLICK_OK").show();
                                        break;
                                }
                            }
                        });
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XAlertDialog(MainActivity.this,XAlertDialog.TYPE_ERROR)
                        .setTitle("Title")
                        .setBtnOkListener(new OnBtnClickListener() {
                            @Override
                            public void onButtonClick(Dialog dialog) {
                                toast.setText("setBtnOkListener").show();
                            }
                        })
                        .setBtnCancelListener(new OnBtnClickListener() {
                            @Override
                            public void onButtonClick(Dialog dialog) {
                                toast.setText("onButtonClick").show();
                            }
                        })
                        .setMsg("Messages...")
                        .show();
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XAlertDialog(MainActivity.this,XAlertDialog.TYPE_WARNING)
                        .setTitle("Title")
                        .setBtnOkListener(new OnBtnClickListener() {
                            @Override
                            public void onButtonClick(Dialog dialog) {
                                toast.setText("setBtnOkListener").show();
                            }
                        })
                        .setBtnCancelListener(new OnBtnClickListener() {
                            @Override
                            public void onButtonClick(Dialog dialog) {
                                toast.setText("onButtonClick").show();
                            }
                        })
                        .setMsg("Messages...")
                        .show();
            }
        });
        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XAlertToast(MainActivity.this,XAlertToast.TYPE_SUCCESS)
                        .show("收货成功！");
            }
        });
        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XAlertToast(MainActivity.this,XAlertToast.TYPE_ERROR)
                        .show("收货失败！");
            }
        });
        findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XAlertToast(MainActivity.this,XAlertToast.TYPE_WARNING)
                        .show("操作警告！");
            }
        });
        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadingDialog(MainActivity.this)
                        .setCancelable(true)
                        .show("操作警告！");
            }
        });
        findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadingDialog(MainActivity.this)
                        .setCancelable(true)
                        .show();
            }
        });
    }

    private NumLoading numLoading;
}
