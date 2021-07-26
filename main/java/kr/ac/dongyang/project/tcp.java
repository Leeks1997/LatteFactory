package kr.ac.dongyang.project;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class tcp extends Service {
    String button;
    Socket socket;

    @Override
    public void onCreate() {
        ServerThread thread = new ServerThread();
        thread.start();
        Log.d("onCreate", "in onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            button = intent.getStringExtra("button");
            OutputPrint thread = new OutputPrint();
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
/*
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Toast.makeText(this, "onTaskRemoved ", Toast.LENGTH_SHORT).show();
        stopSelf();
    }
*/
    class ServerThread extends Thread {

        public void run(){
            try{
                int port = 9001;
                ServerSocket server = new ServerSocket(port);
                while(true){

                    Log.d("wating", "wating accept");
                    socket = server.accept();
                    Log.d("socket.accept", String.valueOf(socket));
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String readValue = reader.readLine();
                    if(readValue != null){
                        Log.d("readValue","in readValue()");
                        if(readValue.equals("fallen")){//넘어졌을때
                            Intent intent = new Intent(getApplicationContext(), message.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP |
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            //Thread.sleep(10000);//10초 기다림
                        }
                    }

                }
            } catch (BindException e) {
                e.printStackTrace();
                Log.d("BindException", "BindException");
            } catch (IOException e) {
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    class OutputPrint extends Thread {

        @Override
        public void run() {
            // OutputStream - 서버에서 클라이언트로 메세지 보내기
            OutputStream out = null;
            try {
                out = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);
                writer.println(button);
                socket.close();
                Log.d("button",button);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
