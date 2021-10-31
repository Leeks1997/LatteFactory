package kr.ac.dongyang.project;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Context;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import kr.ac.dongyang.project.service.gyroService;

public class MainActivity2 extends AppCompatActivity {
    ImageButton blbx;
    ImageButton back;
    Button information;
    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private TextView home_user_name;


    private static final String TAG = "MA2";
    SharedPreferences setting;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftermain);
        //낙상감지 서비스 시작
        startService(new Intent(MainActivity2.this, gyroService.class));


        //subActivity로 이동하는 버튼
        blbx = findViewById(R.id.blbx);
        back = findViewById(R.id.back);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        home_user_name = findViewById(R.id.home_user_name);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d(TAG, token);
                        editor.putString("token",token);
                        editor.apply();
                        String id = setting.getString("id","");
                        UpdateToken.update(id, token);
                    }
                });
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.medicine:
                        Intent NewActivity = new Intent(getApplicationContext(), edit_medicine.class);
                        startActivity(NewActivity);
                        break;

                    case R.id.call:
                        Intent MedActivity = new Intent(getApplicationContext(), phoneDeviceLists.class);
                        startActivity(MedActivity);
                        break;
                    case R.id.timeSet:
                        Intent SetActivity = new Intent(getApplicationContext(), timer.class);
                        startActivity(SetActivity);
                        break;
                }
                return true;
            }
        });
    }

    //로그아웃
    public void onClick(View view) {
        //SharedPreferences setting;
        //SharedPreferences.Editor editor;
        //setting = getSharedPreferences("setting", 0);
        //editor= setting.edit();
        //editor.remove("id");
        //editor.remove("password");
        Intent intentLogout = new Intent(this, MainActivity.class);
        intentLogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intentLogout);
        finish();
    }
    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 1.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 1.5초가 지났으면 Toast 출력
        // 2000 milliseconds = 2.0 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.0초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.0초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            finishAffinity();
            toast.cancel();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                Intent intent=getIntent();
                String name = intent.getStringExtra("name");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}