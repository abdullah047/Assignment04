package com.example.abdullah.assignment04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private android.widget.Button startBtn;
    private android.widget.EditText editText;
    private android.widget.Button stopBtn;
    private android.widget.TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView2 = (TextView) findViewById(R.id.textView2);
        this.stopBtn = (Button) findViewById(R.id.stopBtn);
        this.editText = (EditText) findViewById(R.id.editText);
        this.startBtn = (Button) findViewById(R.id.startBtn);
        EventBus.getDefault().register(this);
        final Intent intent = new Intent(MainActivity.this, MyService.class);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please enter the time limit first.", Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("timeLimit", editText.getText().toString());
                    startService(intent);
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopService(intent);
            }
        });



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage eventMessage) {

        textView2.setText(eventMessage.getServiceTime()+"%");

    }
}
