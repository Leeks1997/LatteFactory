package kr.ac.dongyang.project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.dongyang.project.loginActivity;


public class MainActivity extends AppCompatActivity {
    ImageButton blbx;
    ImageButton back;
    Boolean autoLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //subActivity로 이동하는 버튼
        blbx = findViewById(R.id.blbx);
        back = findViewById(R.id.back);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences setting;
        SharedPreferences.Editor editor;
        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        autoLogin = setting.getBoolean("chx1", false);
        Log.i("test", String.valueOf(autoLogin));

        // 자동로그인 구현
        if(autoLogin){
            Log.i("test",  "자동로그인");
            String loginId = setting.getString("id", "");
            String loginPassword = setting.getString("password", "");
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) { //로그인에 성공한 경
                            editor.putString("id", loginId);
                            editor.putString("password", loginPassword);
                            editor.putBoolean("chx1",true);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                        } else { //로그인에 실패한 경우
                            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(loginId, loginPassword, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(loginRequest);

        }
        //로그인 전에는 블랙박스, 후방카메라 막히게!
       /* blbx.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent1 = new Intent(this, blbx.class);
            startActivity(intent1);
            finish();
        });
        back.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent2 = new Intent(this, back.class);
            startActivity(intent2);
            finish();
        });

        */
    }

    //로그인 액티비티는 안되서 다른 방법으로 진행
    public void onClick(View view) {
        Intent intentLogin = new Intent(this, loginActivity.class);
        startActivity(intentLogin);
        finish();
    }

}
