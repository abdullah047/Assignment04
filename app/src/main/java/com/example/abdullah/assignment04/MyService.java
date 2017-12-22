package com.example.abdullah.assignment04;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


public class MyService extends Service {


    String timeLimit = null;
    CountDownTimer countDownTimer;

    int progress = 0;
    int reTime = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(MyService.this, "Service Start", Toast.LENGTH_SHORT).show();
        timeLimit = intent.getStringExtra("timeLimit");

        countDownTimer = new CountDownTimer(Integer.parseInt(timeLimit)*1000,1000){

            @Override
            public void onTick(long l) {

                reTime++;
                int tTime =  Integer.parseInt(timeLimit);
                progress = (reTime*100)/tTime;
                EventMessage eventMessage = new EventMessage();
                eventMessage.setServiceTime(String.valueOf(progress));
                EventBus.getDefault().postSticky(eventMessage);


            }

            @Override
            public void onFinish() {
                progress = 100;
                EventMessage eventMessage = new EventMessage();
                eventMessage.setServiceTime(String.valueOf(progress));
                EventBus.getDefault().postSticky(eventMessage);
                stopSelf();

            }
        }.start();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(MyService.this, "Service Stop", Toast.LENGTH_SHORT).show();
    }


}


