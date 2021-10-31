package kr.ac.dongyang.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MyInfo extends AppCompatActivity {

    private SharedPreferences settingPrefs;

    private EditText user_pass, user_phone, user_email, user_emer1, user_emer2, user_emer3, user_disease, user_medicine;
    private String password, phone, email, disease, medicine, emCol1, emCol2, emCol3;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        settingPrefs = getSharedPreferences("setting", MODE_PRIVATE);

        TextView edit_id = findViewById(R.id.edit_id);
        TextView user_name = findViewById(R.id.user_name);
        user_pass = findViewById(R.id.user_pass);
        user_phone = findViewById(R.id.user_phone);
        user_email = findViewById(R.id.user_email);
        user_emer1 = findViewById(R.id.user_emer1);
        user_emer2 = findViewById(R.id.user_emer2);
        user_emer3 = findViewById(R.id.user_emer3);
        user_disease = findViewById(R.id.user_disease);
        user_medicine = findViewById(R.id.user_medicine);
        Button user_update = findViewById(R.id.user_update);
        Button user_quit = findViewById(R.id.user_quit);

        String loginId = settingPrefs.getString("id", "");

        String name = settingPrefs.getString("name", "");
        password = settingPrefs.getString("password", "");
        phone = settingPrefs.getString("phone", "");
        email = settingPrefs.getString("email", "");
        disease = settingPrefs.getString("disease", "");
        medicine = settingPrefs.getString("medicine", "");
        emCol1 = settingPrefs.getString("emCol1", "");
        emCol2 = settingPrefs.getString("emCol2", "");
        emCol3 = settingPrefs.getString("emCol3", "");

        Log.d("MyInfo", loginId);
        Log.d("MyInfo", name);

        edit_id.setText(loginId);
        user_name.setText(name);
        user_pass.setText(password);
        user_phone.setText(phone);
        user_email.setText(email);
        user_emer1.setText(emCol1);
        user_emer2.setText(emCol2);
        user_emer3.setText(emCol3);
        user_disease.setText(disease);
        user_medicine.setText(medicine);

        // update 버튼 눌렀을 때
        user_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = user_pass.getText().toString();
                phone = user_phone.getText().toString();
                email = user_email.getText().toString();
                emCol1 = user_emer1.getText().toString();
                emCol2 = user_emer2.getText().toString();
                emCol3 = user_emer3.getText().toString();
                disease = user_disease.getText().toString();
                medicine = user_medicine.getText().toString();

                // 입력하지 않았을 경우
                if (password.equals("") || phone.equals("") || email.equals("") || emCol1.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfo.this);
                    dialog = builder.setMessage("필수항목을 모두 입력해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfo.this);
                builder.setCancelable(false);
                builder.setMessage("수정하시면 이전의 정보들은 모두 변경됩니다. 정말 수정하시겠습니까?");
                builder.setNegativeButton("아니오", null);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("MyInfo", response);

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) { // 정보 update 성공
                                        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();

                                        settingPrefs.edit()
                                                .putString("password", password)
                                                .putString("phone", phone)
                                                .putString("email", email)
                                                .putString("emCol1", emCol1)
                                                .putString("emCol2", emCol2)
                                                .putString("emCol3", emCol3)
                                                .putString("disease", disease)
                                                .putString("medicine", medicine)
                                                .commit();

                                        Intent intent = new Intent(MyInfo.this, MainActivity2.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        MyInfoRequest myinfoRequest = new MyInfoRequest(loginId, password, phone, email, emCol1, emCol2, emCol3, disease,
                                medicine, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MyInfo.this);
                        queue.add(myinfoRequest);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // 탈퇴 버튼 눌렀을 때
        user_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MyInfo.this);
                builder1.setCancelable(false);
                builder1.setMessage("탈퇴하시면 회원님의 정보는 모두 삭제됩니다. 정말 탈퇴하시겠습니까?");
                builder1.setNegativeButton("아니오", null);
                builder1.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) { // 정보 update 성공
                                        Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                                        settingPrefs.edit().clear().commit();
                                        Intent intent = new Intent(MyInfo.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        MyInfoDelete myinfoDelete = new MyInfoDelete(loginId, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MyInfo.this);
                        queue.add(myinfoDelete);
                    }
                });
                AlertDialog dialog = builder1.create();
                dialog.show();
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            private String getString(JSONObject from, String name) {
                if (from.isNull(name)) {
                    return "";

                } else {
                    return from.optString(name);
                }
            }

            @Override
            public void onResponse(String response) {
                Log.d("MyInfo", response);

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject result = jsonResponse.getJSONArray("result").getJSONObject(0);


                    String U_name = getString(result, "name");
                    String U_phone = getString(result, "phone");
                    String U_email = getString(result, "email");
                    String U_emCol1 = getString(result, "emCol1");
                    String U_emCol2 = getString(result, "emCol2");
                    String U_emCol3 = getString(result, "emCol3");
                    String U_disease = getString(result, "disease");
                    String U_medicine = getString(result, "medicine");

                    settingPrefs.edit()
                            .putString("name", U_name)
                            .putString("phone", U_phone)
                            .putString("email", U_email)
                            .putString("emCol1", U_emCol1)
                            .putString("emCol2", U_emCol2)
                            .putString("emCol3", U_emCol3)
                            .putString("disease", U_disease)
                            .putString("medicine", U_medicine)
                            .commit();

                    // Local 에 저장되어 있는 정보와 서버의 정보가 다른 경우, 서버의 정보를 보여준다.

                    if (!name.equals(U_name)) {
                        user_name.setText(U_name);
                    }

                    if (!phone.equals(U_phone)) {
                        user_phone.setText(U_phone);
                    }

                    if (!email.equals(U_email)) {
                        user_email.setText(U_email);
                    }

                    if (!emCol1.equals(U_emCol1)) {
                        user_emer1.setText(U_emCol1);
                    }

                    if (!emCol2.equals(U_emCol2)) {
                        user_emer2.setText(U_emCol2);
                    }

                    if (!emCol3.equals(U_emCol3)) {
                        user_emer3.setText(U_emCol3);
                    }

                    if (!disease.equals(U_disease)) {
                        user_disease.setText(U_disease);
                    }

                    if (!medicine.equals(U_medicine)) {
                        user_medicine.setText(U_medicine);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        };

        MyInfoSelect myinfoSelect = new MyInfoSelect(loginId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyInfo.this);
        queue.add(myinfoSelect);
    }
}
