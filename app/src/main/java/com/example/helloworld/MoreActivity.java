package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox cb_0;
    private CheckBox cb_1;
    private CheckBox cb_2;
    private CheckBox cb_3;
    private CheckBox cb_4;
    String str1="";
    String str2="";
    String str3="";
    String str4="";
    String str0="";
    String str="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        //初始化多选功能的按钮
        initMore();
        //设置监听器
        setListener();
        //设置监听器
        // 初始化控件对象
        Button btn_add = findViewById(R.id.btn_add);
        // 绑定点击监听器
        btn_add.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent();
            intent.putExtra("mess",str);
            Log.v("MoreActivity得到的结果： ",str);
            setResult(RESULT_OK,intent);
            this.finish();
        }
    }
    CompoundButton.OnCheckedChangeListener myCheckChangelistener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // TODO Auto-generated method stub
            //设置TextView的内容显示CheckBox的选择结果
            setText();
        }
    };
    //把多选框选中的东西放到主界面的text5上面，
    // 并且把选中的记录放到数据库。
    private void setText()
    {

        if(cb_0.isChecked())
        {
            str0 = cb_0.getText().toString();
            cb_1.setChecked(false);
            cb_2.setChecked(false);
            cb_3.setChecked(false);
            cb_4.setChecked(false);
//            Log.v("cb_0: ",str0);
        }
        else{
            str0="";
        }
        if(cb_0.isChecked()==false)
        {
            if(cb_1.isChecked())
            {
                str1= cb_1.getText().toString();
                //            Log.v("cb_1: ",str1);
            }
            else{
                str1="";
            }
            if(cb_2.isChecked())
            {
                str2 = cb_2.getText().toString();
                //            Log.v("cb_2: ",str2);
            }
            else{
                str2="";
            }
            if(cb_3.isChecked())
            {
                str3 = cb_3.getText().toString();
                //            Log.v("cb_3: ",str3);
            }
            else{
                str3="";
            }
            if(cb_4.isChecked())
            {
                str4 = cb_4.getText().toString();
                //            Log.v("cb_4: ",str4);
            }
            else{
                str4="";
            }
        }
        str=str0+" "+str1+" "+str2+" "+str3+" "+str4;
        Log.v("choicesResult: ",str);
    }
    private void initMore(){
        cb_0=findViewById(R.id.cb_0);
        cb_1=findViewById(R.id.cb_1);
        cb_2=findViewById(R.id.cb_2);
        cb_3=findViewById(R.id.cb_3);
        cb_4=findViewById(R.id.cb_4);
    }
    //
    //给多选框设置监听器

    private void setListener(){
        cb_0.setOnCheckedChangeListener(myCheckChangelistener);
        cb_1.setOnCheckedChangeListener(myCheckChangelistener);
        cb_2.setOnCheckedChangeListener(myCheckChangelistener);
        cb_3.setOnCheckedChangeListener(myCheckChangelistener);
        cb_4.setOnCheckedChangeListener(myCheckChangelistener);
    }
}
