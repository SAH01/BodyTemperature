package com.example.helloworld;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESQUEST=100;
    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;
    private TextView text5;
    private Button     info;
    String tempstr="";
    private MyDBHelper mDBHelper;
    private DBOpenHelperLogin mDBOpenHelperLogin;             //定义数据库操作类
    private WenDate           wendate=new WenDate();
    private TextView mTv = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    //获取地址
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String town = location.getTown();
            //获取乡镇信息
            text4=(EditText)findViewById(R.id.tv_text4);
            text4.setText(country+" " +province+" "+city+" "+district+" "+town+" "+street+" ");
        }
    }
    //自动获取地址
    public void autoAddress(View view)
    {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        mLocationClient.setLocOption(option);
        //注册监听函数
        mLocationClient.start();
    }

    //
    //onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MainActivity","MainActivity启动");
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        /*
            初始化绑定数据库！！！！！
            很重要
         */
        mDBOpenHelperLogin = new DBOpenHelperLogin(this);
        mDBHelper = new MyDBHelper(this,"amydb.db",1);

        //
        text1=findViewById(R.id.tv_text1);
        text2=findViewById(R.id.tv_text2);
        text3=findViewById(R.id.tv_text3);
        text4=findViewById(R.id.tv_text4);
        text5=findViewById(R.id.tv_text5);
        info=findViewById(R.id.info);
        initView();
        EditText et_tiwen=findViewById(R.id.tv_text3);
        et_tiwen.setText("体温 36.2℃");
        //
        //把名字显示到界面
        showNmae();
    }
    //
    //接收复选框传过来的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case RESULT_OK:
                tempstr=data.getExtras().getString("mess");
                Log.v("输出“特殊情况”获取结果 ",data.getExtras().getString("mess"));
                text5.setText(tempstr);
                break;
            default:
                break;
        }
    }

    private void initView() {
        // 初始化控件对象
        Button mBtMainLogout = findViewById(R.id.bt_main_logout);
        // 绑定点击监听器
        mBtMainLogout.setOnClickListener(this);
        text5=findViewById(R.id.tv_text5);
        text5.setOnClickListener(this);
    }
    //点击跳转事件点击事件
    public void onClick(View view) {
        if (view.getId() == R.id.bt_main_logout) {
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
            finish();
        }
        if (view.getId() == R.id.tv_text5) {
            Intent intent = new Intent(this, MoreActivity.class);
            intent.putExtra("flag",RESQUEST);
            startActivityForResult(intent,RESQUEST);
        }
        if(view.getId() == R.id.info){

            //分别获取注册人数和填写人数  注册人数—填写人数那就是没有填表的人数
            int registers=0;            //注册
            int users=0;                //填表

            registers=getInfoLogin();
            users=getInfo();

            int result= registers-users;
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            if (result>=0)
            {
                builder.setTitle("统计结果").setIcon(R.mipmap.info_show).setMessage("注册人数:  "+registers+"\n"+"填表人数:  "
                +users+"\n"+"未填写人数:  "+result)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog ad=builder.create();
                ad.show();
            }
            else{
                builder.setTitle("出错啦！").setIcon(R.mipmap.ic_launcher_round).setMessage("数据解析异常...")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog ad=builder.create();
                ad.show();
            }
        }
    }
    //
    public void autoTimeAndDate(View view)
    {
        text2=(EditText)findViewById(R.id.tv_text2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        text2.setText(simpleDateFormat.format(date));
    }
    public void createDB(View view)
    {
        MyDBHelper mydbh=new MyDBHelper(this,"amydb.db",1);
        SQLiteDatabase sqldb=mydbh.getReadableDatabase();
    }
    public void insertDB(View view)
    {
        MyDBHelper mydbh=new MyDBHelper(this,"amydb.db",1);
        SQLiteDatabase sqldb=mydbh.getReadableDatabase();
        ContentValues contentvalues=new ContentValues();
        text1=(EditText)findViewById(R.id.tv_text1);
        text2=(EditText)findViewById(R.id.tv_text2);
        text3=(EditText)findViewById(R.id.tv_text3);
        text4=(EditText)findViewById(R.id.tv_text4);
        contentvalues.put("name",text1.getText().toString());
        contentvalues.put("dateandtime",text2.getText().toString());
        contentvalues.put("address",text4.getText().toString());
        contentvalues.put("wendu",text3.getText().toString());
        contentvalues.put("more",text5.getText().toString());
        long flag=sqldb.insert("personwendu",null,contentvalues);
        Toast.makeText(this,flag+"条数据加入成功",Toast.LENGTH_LONG).show();
    }
    //
    //查看数据
    public void queryData(View view)
    {
        MyDBHelper mydbh=new MyDBHelper(this,"amydb.db",1);
        SQLiteDatabase sqldb=mydbh.getReadableDatabase();
        Cursor cursor=sqldb.rawQuery("select * from personwendu",null);
        String str="";
        if(cursor.moveToFirst())
        {
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String dateandtime=cursor.getString(cursor.getColumnIndex("dateandtime"));
                String address=cursor.getString(cursor.getColumnIndex("address"));
                String wendu=cursor.getString(cursor.getColumnIndex("wendu"));
                String more= cursor.getString(cursor.getColumnIndex("more"));
                str=str+name+"|"+dateandtime+"|"+wendu+"\n"+address+"\n"+"-----"+more;
            }while(cursor.moveToNext());
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("历史记录").setIcon(R.mipmap.info).setMessage(str)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog ad=builder.create();
        ad.show();
    }
    public void updateData(View view)
    {
        MyDBHelper mydbh=new MyDBHelper(this,"mydb.db",1);
        SQLiteDatabase sqldb=mydbh.getReadableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("name","hh");
        contentvalues.put("age","18");
        int flag=sqldb.update("person",contentvalues,"name=?",new String[]{"李明"});
        Toast.makeText(this,"已有"+flag+"条数据修改",Toast.LENGTH_LONG).show();
    }
    public void deleteDate(View view)
    {
        MyDBHelper mydbh=new MyDBHelper(this,"mydb.db",1);
        SQLiteDatabase sqldb=mydbh.getReadableDatabase();
        int flag=sqldb.delete("person","id=1",null);
        Toast.makeText(this,"已有"+flag+"条数据删除",Toast.LENGTH_LONG).show();
    }
    //把姓名从数据库里读取出来然后显示到控件上
    public void showNmae(){
        //先定义一个User类找到数据库最后一条数据里的姓名信息
        List list= new ArrayList<User>();
        list= mDBOpenHelperLogin.getAllData();
        if(list.size()!=0){
            TextView et_name=(TextView) findViewById(R.id.tv_text1);
            User user = (User) list.get(list.size()-1);
            String name1 = user.getUsername();
            Log.v("输出查询到的最后一个姓名：",name1);
            et_name.setText(name1);
        }
        else{
            Log.v("输出查询到的最后一个姓名：","查询失败！！！");
        }
    }
    //
    //查询出填写报表的人数
    public int getInfo(){
        ArrayList<WenDate> list;
        list= mDBHelper.getAllData();
        Log.v("输出填表人数：  ", String.valueOf(list.size()));
        if(list.size()!=0)
        return list.size();
        else
        {
            return -1;
        }
    }
    //
    //查询注册人数
    public int getInfoLogin(){
        ArrayList<User> list= mDBOpenHelperLogin.getAllData();
        Log.v("输出注册人数：  ", String.valueOf(list.size()));
        if(list.size()!=0)
            return list.size();
        else
        {
            return -1;
        }
    }
}