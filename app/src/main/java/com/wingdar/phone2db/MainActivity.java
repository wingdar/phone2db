package com.wingdar.phone2db;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ok_button = (Button) findViewById(R.id.button);
        ok_button.setOnClickListener(ok_btn);

        final TextView text_view = (TextView) findViewById(R.id.Text_view);
        //final ListView ListV = (ListView) findViewById(R.id.LV1)
        text_view.setMovementMethod(ScrollingMovementMethod.getInstance());

        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String data = con.getData();
                Log.v("OK",data);
                text_view.post(new Runnable() {
                    public void run() {
                        text_view.setText(data);
                        //ListV.setTextFilterEnabled(data);
                    }
                });

            }
        }).start();
    }

    private Button.OnClickListener ok_btn = v -> {

        final TextView text_view = (TextView) findViewById(R.id.Text_view);

        Toast toast = Toast.makeText(this, R.string.input_but, Toast.LENGTH_SHORT);
        toast.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 取得 EditText 資料
                final EditText edit_text = (EditText) findViewById(R.id.et2);
                String stringdata = edit_text.getText().toString();
                final EditText edit_text1 = (EditText) findViewById(R.id.et1);
                String stringid = edit_text1.getText().toString();
                // 清空 EditText
                edit_text.post(new Runnable() {
                    public void run() {
                        edit_text.setText("");
                        edit_text1.setText("");
                    }
                });
                // 將資料寫入資料庫
                MysqlCon con = new MysqlCon();

                con.insertData(Float.parseFloat(stringdata), stringid );
                // 讀取更新後的資料
                final String updata = con.getData();
                Log.v("OK", updata);
                text_view.post(new Runnable() {
                    public void run() {
                        text_view.setText(updata);
                    }
                });

            }
        }).start();
    };
}