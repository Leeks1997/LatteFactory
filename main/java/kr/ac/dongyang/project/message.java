package kr.ac.dongyang.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class message extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        Button btnY = (Button)findViewById(R.id.btnY);
        Button btnN = (Button)findViewById(R.id.btnN);
        btnY.setOnClickListener(new View.OnClickListener(){//넘어졌을 경우
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"넘어짐",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), tcp.class);
                intent.putExtra("button", "btnY");
                //메시지 보내기 구현
                
                startService(intent);
                finish();
            }
        });
        btnN.setOnClickListener(new View.OnClickListener(){//넘어지지 않았을경우
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"안넘어짐",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), tcp.class);
                intent.putExtra("button", "btnN");
                startService(intent);
                finish();
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), tcp.class);
                intent.putExtra("button", "btnN");
                startService(intent);
                finish();
            }
        }, 10000);//10초
    }

    @Override
    public void onBackPressed() {//메시지 화면에서 뒤로가기 누를때 꺼지게
        //Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), tcp.class);
        intent.putExtra("button", "btnN");
        startService(intent);
        super.onBackPressed();
    }
}