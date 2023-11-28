package com.example.cpuload;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Looper;
import java.lang.Math;

public class FirstActivity extends Activity {
    private Thread thread;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1 = (Button) findViewById(R.id.button);
        Button button_stop = (Button) findViewById(R.id.button_stop);
        button_stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                stopLoop();
            }
        }
        );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FirstActivity.this, "Hello, world!", Toast.LENGTH_SHORT).show();
                start_loop();
            }
        });


    }

    private void start_loop_mainthread(){
        while(true)
        {
            for(int i=0; i<9600000; i++){

            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void start_loop() {
        Toast.makeText(FirstActivity.this, "子线程开启cpu将保持", Toast.LENGTH_SHORT).show();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    for(int i=0; i<9600000; i++){

                    }
                    try {
                        //Thread.sleep(10);
                        thread.sleep(10);
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                        // 当线程被中断时，执行以下代码...
                        Thread.currentThread().interrupt(); // 重新设置中断状态，确保其他线程可以检测到中断状态。
                        // 在这里可以添加你的清理代码，例如释放资源等。
                        break; // 退出循环，停止线程。
                    }
                }
            }
        });
        thread.start();
    }
    private void stopLoop() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                thread.interrupt(); // 停止线程。
                Toast.makeText(FirstActivity.this, "子线程已被终止", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void start_loop_sin(){
        final int SAMPLING_COUNT = 200;
        final double PI = 3.14159265;
        final int TOTAL_AMPLITUDE = 300;
        int[] busySpan = new int[SAMPLING_COUNT];
        int amplitude = TOTAL_AMPLITUDE / 2;
        double radian = 0.0;
        double radianIncrement = 2.0 / (double)SAMPLING_COUNT;
        for (int i = 0; i < SAMPLING_COUNT; i++) {
            busySpan[i] = (int) (amplitude + (Math.sin(PI * radian) * amplitude));
            radian += radianIncrement;
        }
        long startTime = 0;
        for (int j = 0; ; j = (j + 1) % SAMPLING_COUNT) {
            startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) <= busySpan[j])
                ;
            try {
                Thread.sleep(TOTAL_AMPLITUDE - busySpan[j]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}