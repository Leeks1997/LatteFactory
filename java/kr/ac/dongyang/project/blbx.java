package kr.ac.dongyang.project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class blbx extends AppCompatActivity {
    Button btnmain, btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16;
    private VideoView videoView; // 비디오를 실행할 수 있게 도와주는 뷰
    private MediaController mediaController; // 재생이나 정지와 같은 미디어 제어 버튼부를 담당
    public final static String videoURL1 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"; //URL 주소는 무조건 https로 시작
    public final static String videoURL2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
    public final static String videoURL3 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4";
    public final static String videoURL4 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";
    public final static String videoURL5 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
    public final static String videoURL6 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4";
    public final static String videoURL7 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4";
    public final static String videoURL8 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4";
    public final static String videoURL9 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4";
    public final static String videoURL10 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4";
    public final static String videoURL11 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4";
    public final static String videoURL12 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4";
    public final static String videoURL13 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4";
    public final static String videoURL14 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    public final static String videoURL15 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    public final static String videoURL16 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blbx);

        videoView = findViewById(R.id.blackbox); // 비디오 뷰 아이디 연결
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController); // 미디어 제어 버튼부 세팅

        //mainactivity로 이동
        btnmain = findViewById(R.id.btnmain);
        btnmain.setOnClickListener((v) -> {
            //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
            Intent intent6 = new Intent(this, MainActivity2.class);
            startActivity(intent6);
            finish();
        });
        //버튼 기능 추가 : 누르면 URL을 URI로 바꾸고 재생
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL1));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL2));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL3));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL4));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL5));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL6));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL7));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL8));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL9));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn10 = findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL10));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn11 = findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL11));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn12 = findViewById(R.id.btn12);
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL12));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn13 = findViewById(R.id.btn13);
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL13));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn14 = findViewById(R.id.btn14);
        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL14));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn15 = findViewById(R.id.btn15);
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL15));
                videoView.requestFocus();
                videoView.start();
            }
        });
        btn16 = findViewById(R.id.btn16);
        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse(videoURL16));
                videoView.requestFocus();
                videoView.start();
            }
        });
        //Error 메세지 표시
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String message;

                switch (what) {
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        message = "MEDIA_ERROR_UNKNOWN";
                        break;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        message = "MEDIA_ERROR_SERVER_DIED";
                        break;
                    default:
                        message = "No What";
                }
                switch (extra) {
                    case MediaPlayer.MEDIA_ERROR_IO:
                        message += ", MEDIA_ERROR_IO";
                        break;
                    case MediaPlayer.MEDIA_ERROR_MALFORMED:
                        message += ", MEDIA_ERROR_MALFORMED";
                        break;
                    case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                        message += ", MEDIA_ERROR_UNSUPPORTED";
                        break;
                    default:
                        message += ", No extra";
                }
                Log.e("error Tag : ", message);
                return true;
            }
        });
    }
}

