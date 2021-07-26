package kr.ac.dongyang.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class back extends Activity{
    Button date;
    Button btnmain;
    WebView web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back);

        //mainactivity로 이동
        date = findViewById(R.id.date);
        btnmain = findViewById(R.id.btnmain);
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

        // 이동 버튼 클릭
        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                web.loadUrl("http://172.20.10.2:5000/stream?src=0"); // 입력한 URL 웹페이지가 웹뷰에 나타남
            }
        });

        btnmain.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent5 = new Intent(this, MainActivity2.class);
            startActivity(intent5);
            finish();
        });
        //mainactivity로 이동
    }

    // WebViewClient를 상속받아 자신의 WebViewClient인 TCWebViewClient 클래스를 정의
    class TCWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}