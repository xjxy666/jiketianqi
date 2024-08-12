package com.example.jiketianqi;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button button10;//折线图跳转
    Button button8;//跳转中国天气网
    NavigationView navigationView;//侧边栏操作
    TextView textView3,textView8;//3是实时时间，8是实时天气
    Toolbar toolbar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button8=(Button)findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://m.weather.com.cn/mweather/101070205.shtml");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        toolbar=findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher_round);
        toolbar.setTitle("极客天气");
        toolbar.setSubtitle("开发者");
        setSupportActionBar(toolbar);//支持添加toolbar
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //添加右上角的菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        button10=(Button)findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

        //以上为点击button2的跳转
        navigationView=findViewById(R.id.navi);
        navigationView.setItemIconTintList(null);
        View view1=navigationView.getHeaderView(0);
        ImageView imageView=view1.findViewById(R.id.imageView5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"要打篮球吗？",Toast.LENGTH_SHORT);
            }
        });
        //以上为点击侧边栏头部时执行的操作
        final TextView textView=view1.findViewById(R.id.textView6);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                textView.setText("您选择的是："+ menuItem.getTitle().toString());
                //得到当前选中的MenuItem的ID,
                int item_id = menuItem.getItemId();
                switch (item_id)
                {
                    case R.id.item1:
                        /* 新建一个Intent对象 */
                        Intent intent = new Intent();
                        /* 指定intent要启动的类 */
                        intent.setClass(MainActivity.this, Main2Activity.class);
                        /* 启动一个新的Activity */
                        startActivity(intent);
                        /* 关闭当前的Activity */
                        MainActivity.this.finish();
                        break;
                    case R.id.item4:
                        MainActivity.this.finish();
                        break;
                }
                return false;
            }
        });
        //以上为点击抽屉更换文字的操作

        textView3=findViewById(R.id.textView3);
        textView8=findViewById(R.id.textView8);
        new TimeThead().start();
        new TimeThead2().start();
        //创建一个时间计时器 放在textview2的位置上，实时刷新text
    }

    public class TimeThead extends Thread{
        @Override
        public void run(){
            super.run();
            do {
                try{
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
    //创建一个线程，每隔一秒刷新一次
    public class TimeThead2 extends Thread{
        @Override
        public void run(){
            super.run();
            Message msg = new Message();
            handler2.sendMessage(msg);
            msg.what = 1;
        }
    }
    //创建更改天气的线程 有bug
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    textView3.setText(new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date(System.currentTimeMillis())));
                    break;
            }
            return false;
        }
    });
    //用系统时间替换
    private Handler handler2 =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            textView8.setText("21/13 ℃ >");
            return false;
        }
    });
    //设置天气刷新 有BUG

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        //得到当前选中的MenuItem的ID,
        int item_id = item.getItemId();
        switch (item_id)
        {
            case R.id.item1:
                /* 新建一个Intent对象 */
                Intent intent = new Intent();
                /* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, Main2Activity.class);
                /* 启动一个新的Activity */
                startActivity(intent);
                /* 关闭当前的Activity */
                MainActivity.this.finish();
                break;
            case R.id.item4:
                MainActivity.this.finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first,menu);
        return true;
    }
}
