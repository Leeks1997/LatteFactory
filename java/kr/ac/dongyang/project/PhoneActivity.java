package kr.ac.dongyang.project;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneActivity extends AppCompatActivity {

    EditText editText_name, editText_phone_number, memId;
    private Button button_save, button_cancel, memR;
    private static String phone, id;
    int selected;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        memId = findViewById(R.id.memId);
        memR = findViewById(R.id.memR);
        editText_phone_number = findViewById(R.id.editText_phone_number);
        button_save = findViewById(R.id.button_save);
        button_cancel = findViewById(R.id.button_cancel);
        selected = R.drawable.unspecified;

        // id 확인
        memR.setOnClickListener(view -> {
            id = memId.getText().toString();
            if(validate) {
                return; // 검증 완료
            }
            if (id.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
                dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
                        dialog = builder.setMessage("회원이 아닙니다. 회원가입이 필요합니다.").setPositiveButton("확인", null).create();
                        dialog.show();

                        memR.setBackgroundColor(Color.LTGRAY);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
                        dialog = builder.setMessage("확인 완료되었습니다.").setNegativeButton("확인", null).create();
                        dialog.show();
                        memId.setEnabled(false); // 아이디값 고정
                        validate = true; // 검증 완료
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            ValidateRequest validateRequest = new ValidateRequest(id, responseListener);
            RequestQueue queue = Volley.newRequestQueue(PhoneActivity.this);
            queue.add(validateRequest);
        });

        // save 버튼 눌렀을 때
        button_save.setOnClickListener(view -> {
            phone = editText_phone_number.getText().toString();

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) { // 비상연락처 추가 성공
                        Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PhoneActivity.this, phoneDeviceLists.class);
                        startActivity(intent);
                        finish();
                    } else { // 추가 실패
                        Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            PhoneRequest phoneRequest = new PhoneRequest(phone, responseListener);
            RequestQueue queue = Volley.newRequestQueue(PhoneActivity.this);
            queue.add(phoneRequest);
        });

        // cancel 버튼 눌렀을 때
        button_cancel.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), phoneDeviceLists.class);
            startActivity(intent);
            finish();
        });

    }
}