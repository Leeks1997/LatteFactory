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
    //int selected;
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
        //selected = R.drawable.unspecified;

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

//                    boolean success = jsonResponse.getBoolean("success");
//
//                    if (success) { // 정보 select 성공

                    String U_name = getString(result, "name");
                    String U_phone = getString(result, "phone");
                    String U_email = getString(result, "email");
                    String U_emCol1 = getString(result, "emCol1");
                    String U_emCol2 = getString(result, "emCol2");
                    String U_emCol3 = getString(result, "emCol3");
                    String U_disease = getString(result, "disease");
                    String U_medicine = getString(result, "medicine");

                    Toast.makeText(getApplicationContext(), "불러왔습니다.", Toast.LENGTH_SHORT).show();

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

//                        Intent intent = new Intent(MyInfo.this, MainActivity2.class);
//                        startActivity(intent);
//                        finish();

//                    } else {
//                        Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
//                        editor.clear();
//                        editor.commit();
//                        return;
//                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
//                    editor.clear();
//                    editor.commit();

                    e.printStackTrace();
                }
            }
        };

        MyInfoSelect myinfoSelect = new MyInfoSelect(loginId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyInfo.this);
        queue.add(myinfoSelect);
    }
}
