package test.com.chestnut.Dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chestnut.Common.ui.Toastc;
import com.chestnut.Dialog.SimpleDialog.SimpleDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toastc toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = new Toastc(this, Toast.LENGTH_LONG);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> stringList = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    stringList.add("Title:"+i);
                }

                new SimpleDialog(MainActivity.this)
                        .setStyle(SimpleDialog.POSITION_CENTER)
                        .setItems(stringList)
                        .setOnItemClick(new SimpleDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(SimpleDialog simpleDialog, View view, int position) {
                                toast.setText("position:"+position).show();
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> stringList = new ArrayList<String>();
                for (int i = 0; i < 15; i++) {
                    stringList.add("Title:"+i);
                }
                new SimpleDialog(MainActivity.this,SimpleDialog.POSITION_BOTTOM)
                        .setItems(stringList)
                        .setOnItemClick(new SimpleDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(SimpleDialog simpleDialog, View view, int position) {
                                toast.setText("position:"+position).show();
                                if (position==2) {
                                    simpleDialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
    }

}
