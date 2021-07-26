package kr.ac.dongyang.project;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class back extends Activity {
    Button btnmain;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back);
        //mainactivity로 이동
        btnmain = findViewById(R.id.btnmain);
        btnmain.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent5 = new Intent(this, MainActivity2.class);
            startActivity(intent5);
            finish();
        });
    }
}
