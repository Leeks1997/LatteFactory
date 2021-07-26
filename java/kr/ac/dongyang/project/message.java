package kr.ac.dongyang.project;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class message extends AppCompatActivity {
    private GpsTracker gpsTracker;
    //putExtra값 받아오기 -> loginActivity에서...
    Intent LoginIntent = getIntent();
    String login = LoginIntent.getStringExtra("id");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        Button btnY = (Button)findViewById(R.id.btnY);
        Button btnN = (Button)findViewById(R.id.btnN);

        btnY.setOnClickListener(new View.OnClickListener(){//넘어졌을 경우
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), tcp.class);
                intent.putExtra("button", "btnY");
                
                //문자 보내기
                gpsTracker = new GpsTracker(getApplicationContext());

                double latitude = gpsTracker.getLatitude();//위도
                double longitude = gpsTracker.getLongitude();//경도
                String address = getCurrentAddress(latitude, longitude);//한글주소

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    String emCol;
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =  new JSONObject(response);
                            Log.d("Array", String.valueOf(jsonObject));
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                int length = jsonObject.length();
                                for(int i=0; i <= length; i++){
                                    emCol = jsonObject.getString(String.valueOf(i));
                                    Log.d("emcol : ", emCol);
                                }
                                String sendMessage = "https://www.google.com/maps/place/" + latitude +","+ longitude;
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(emCol, null, sendMessage, null, null);
                                Log.d("address latitude", String.valueOf(latitude));
                                Log.d("address longitude", String.valueOf(longitude));
                                Log.d("address",address);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // Volley 라이브러리를 이용해 실제 서버와 통신을 구현하는 부분
                MessageRequest mRequest = new MessageRequest(login, responseListener);
                RequestQueue queue = Volley.newRequestQueue(message.this);
                queue.add(mRequest);

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

    //GPS 도로명 주소로 변환
    public String getCurrentAddress( double latitude, double longitude) {


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }
}