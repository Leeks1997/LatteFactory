package kr.ac.dongyang.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class edit_medicine extends Activity {

    private Button memR;
    private EditText memId, edit_1, edit_2;
    private static String id, disease, meditation;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        // 값 가져오기
        memId = findViewById(R.id.memId);
        edit_1 = findViewById(R.id.edit_1);
        edit_2 = findViewById(R.id.edit_2);
        memR = findViewById(R.id.memR);
        Button insert = findViewById(R.id.insert);

        // id 확인
        memR.setOnClickListener(view -> {
            id = memId.getText().toString();
            if(validate) {
                return; // 검증 완료
            }
            if (id.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_medicine.this);
                dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    AlertDialog.Builder builder = new AlertDialog.Builder(edit_medicine.this);
                    if (success) {
                        dialog = builder.setMessage("회원이 아닙니다. 회원가입이 필요합니다.").setPositiveButton("확인", null).create();
                        dialog.show();
                        memR.setBackgroundColor(Color.LTGRAY);
                    } else {
                        dialog = builder.setMessage("확인 완료되었습니다.").setNegativeButton("확인", null).create();
                        memId.setEnabled(false); // 아이디값 고정
                        validate = true; // 검증 완료
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            ValidateRequest validateRequest = new ValidateRequest(id, responseListener);
            RequestQueue queue = Volley.newRequestQueue(edit_medicine.this);
            queue.add(validateRequest);
        });

        // 입력 버튼이 눌렸을 때
        insert.setOnClickListener(view -> {
            // 현재 입력된 정보를 string으로 가져오기
            id = memId.getText().toString();
            disease = edit_1.getText().toString();
            meditation = edit_2.getText().toString();

            // 아이디 중복체크 했는지 확인
            if (!validate) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_medicine.this);
                dialog = builder.setMessage("회원 여부를 확인하세요.").setNegativeButton("확인", null).create();
                dialog.show();
                return;
            }

            // 입력 시작
            Response.Listener<String> responseListener = response -> {
                try {
                    //JSON 형태로 변형해 받음
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    // 결과
                    if(success) { // 성공
                        Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(edit_medicine.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    } else { // 성공하지 못했다면
                        Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            // Volley 라이브러리를 이용해 실제 서버와 통신을 구현하는 부분
            medicineRequest medicineRequest = new medicineRequest(id, disease, meditation, responseListener);
            RequestQueue queue = Volley.newRequestQueue(edit_medicine.this);
            queue.add(medicineRequest);
        });

    }
}

