package com.example.administrator.wifitest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SetActivity extends Activity {
        private TextView come;
        private CheckBox ch_box;
        String keep="false";
        private EditText edittime;
        String time;
    private static final String fileName = "sharedfile";// 定义保存的文件的名称
    private long clickTime=0;

    //重写onKeyDown方法,实现双击退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再次点击退出",  Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            Log.e(TAG, "exit application");
            this.finish();
            System.exit(0);
        }
    }
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.set);
            come= (TextView) findViewById(R.id.come);
            ch_box= (CheckBox) findViewById(R.id.ch_box);
            edittime= (EditText) findViewById(R.id.edittime);
            Bundle bundle = this.getIntent().getExtras();

        //获取缓存
        SharedPreferences share = super.getSharedPreferences(fileName,
                MODE_PRIVATE);
        edittime.setText( share.getString("timeStr",10+""));// 如果没有值，则显示“10”
        keep=share.getString("keep",false+"");
        if(keep.equals("true")){
            ch_box.setChecked(true);
        }
        //确定
            come.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(SetActivity.this,wifiActivity.class);
                    Bundle bundle=new Bundle();
                    time=edittime.getText().toString();
                    bundle.putString("time", time);
                    bundle.putString("keep", keep);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());//按返回按钮时,结束本Activity的运行
                }
            });
            //屏幕常亮复选框事件
            ch_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        keep="true";
                        Toast.makeText(SetActivity.this,"保持常亮",Toast.LENGTH_SHORT).show();
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }else {
                        keep="false";
                        Toast.makeText(SetActivity.this,"取消常亮",Toast.LENGTH_SHORT).show();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                }
            });
        }

}
