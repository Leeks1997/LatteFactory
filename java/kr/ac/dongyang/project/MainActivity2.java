package kr.ac.dongyang.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity2 extends AppCompatActivity {
    ImageButton blbx;
    ImageButton back;

    SharedPreferences setting;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftermain);
        //낙상감지 서비스 시작
        startService(new Intent(MainActivity2.this, tcp.class));


        //subActivity로 이동하는 버튼
        blbx = findViewById(R.id.blbx);
        back = findViewById(R.id.back);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();


        blbx.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent1 = new Intent(this, blbx.class);
            startActivity(intent1);
        });
        back.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent2 = new Intent(this, back.class);
            startActivity(intent2);
        });
    }

    //로그아웃
    public void onClick(View view) {
        editor.putBoolean("chx1",false);
        editor.commit();
        Intent intentLogout = new Intent(this, MainActivity.class);
        startActivity(intentLogout);
        finish();
    }
    //로그인 이후 메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                Intent NewActivity = new Intent(getApplicationContext(), phoneDeviceLists.class);
                startActivity(NewActivity);
                break;

            case R.id.menu1:
                Intent MedActivity = new Intent(getApplicationContext(), edit_medicine.class);
                startActivity(MedActivity);
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {    }
}