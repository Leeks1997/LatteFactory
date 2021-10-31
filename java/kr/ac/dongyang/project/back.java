package kr.ac.dongyang.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class back extends Activity {
    Button btnmain;
    Button capture;
    WebView web;
    LinearLayout container;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back);

        btnmain = findViewById(R.id.btnmain);
        container = (LinearLayout)findViewById(R.id.main_container);
        capture = findViewById(R.id.capture);
        web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new TCWebViewClient()); // TCWebViewClient 클래스를 생성하여 웹뷰에 대입

        // WebSettings 클래스를 이용하여 줌 버튼 컨트롤이 화면에 보이게 함
        WebSettings webSet = web.getSettings();
        webSet.setBuiltInZoomControls(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setDisplayZoomControls(false);
        web.loadUrl("http://192.168.35.80:5000/stream?src=0"); // 입력한 URL 웹페이지가 웹뷰에 나타남

        btnmain.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent5 = new Intent(this, MainActivity2.class);
            startActivity(intent5);
            finish();
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String folder = "LatteFactory"; // 폴더 이름
                try {
                    // 현재 날짜로 파일을 저장하기
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    // 년월일시분초
                    Date currentTime_1 = new Date();
                    String dateString = formatter.format(currentTime_1);
                    File sdCardPath = Environment.getExternalStorageDirectory();
                    File dirs = new File(Environment.getExternalStorageDirectory(), folder);
                    if (!dirs.exists()) { // 원하는 경로에 폴더가 있는지 확인
                        dirs.mkdirs(); // Test 폴더 생성
                        Log.d("CAMERA_TEST", "Directory Created");
                    }
                    container.buildDrawingCache();
                    Bitmap captureView = container.getDrawingCache();
                    FileOutputStream fos;
                    String save;
                    try {
                        save = sdCardPath.getPath() + "/" + folder + "/" + dateString + ".jpg";
                        // 저장 경로
                        fos = new FileOutputStream(save);
                        captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos); // 캡쳐
                        // 미디어 스캐너를 통해 모든 미디어 리스트를 갱신시킨다.
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), dateString + ".jpg 저장",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("Screen", "" + e.toString());
                }
            }
        });
    }

    // WebViewClient를 상속받아 자신의 WebViewClient인 TCWebViewClient 클래스를 정의
    class TCWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}