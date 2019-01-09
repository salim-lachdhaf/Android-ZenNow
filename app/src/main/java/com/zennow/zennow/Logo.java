package com.zennow.zennow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Logo extends AppCompatActivity {

    private TextView logo;
    private Timer timer;
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        logo=(TextView)this.findViewById(R.id.logo);

        timer=new Timer();
        i=0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (i>=30){
                        startActivity(new Intent(Logo.this,Login.class));
                        timer.cancel();
                        finish();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logo.setAlpha(logo.getAlpha()-Float.valueOf("0.0328"));
                        }
                    });
                }catch (Exception e){e.printStackTrace();}
                i++;
            }
        },0,100);
    }
}
