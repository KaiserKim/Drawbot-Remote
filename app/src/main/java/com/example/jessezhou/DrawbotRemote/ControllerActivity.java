package com.example.jessezhou.DrawbotRemote;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ControllerActivity extends Activity {

    private Handler handler;

    private ImageButton bUp, bDown, bRight, bLeft;
    private ImageButton penUp, penDown;
    private ImageView drawTT, calibrate;

    private BluetoothDevice desiredDevice;
    private DrawbotCommand controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        init();
        startDPadlisteners();
        startPenListeners();
        startMidSectionListeners();
    }

    private void init(){

        // Set up a handler to handle Bluetooth messages
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case Constants.BLUETOOTH_CONNECT:
                        if(msg.arg1 == Constants.CONNECTION_SUCCESS){
                            Toast.makeText(getApplicationContext(), "Connection worked!", Toast.LENGTH_SHORT).show();
                            System.out.println("Handler is working");
                        }
                        else if(msg.arg1 == Constants.CONNECTION_FAILED){
                            Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        };

        //Get the base application (which has always been running) and initialize
        //an outputstream using the global BTsocket
        desiredDevice = getIntent().getExtras().getParcelable("DESIRED_DEVICE");
        connect(desiredDevice);

        //Initialize the UI elements
        bUp = (ImageButton)findViewById(R.id.buttonForward);
        bDown = (ImageButton)findViewById(R.id.buttonDown);
        bRight = (ImageButton)findViewById(R.id.buttonRight);
        bLeft = (ImageButton)findViewById(R.id.buttonLeft);
        penUp = (ImageButton)findViewById(R.id.buttonPenUp);
        penDown = (ImageButton)findViewById(R.id.buttonPenDown);
        drawTT = (ImageView)findViewById(R.id.buttonDrawTT);
        calibrate = (ImageView)findViewById(R.id.buttonRecalibrate);
    }

    private void startDPadlisteners() {

        bUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        controller.forward();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        bDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        controller.backward();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        bRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        controller.turnRight();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        bLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        controller.turnLeft();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });
    }

    private void startPenListeners(){
        penUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.penUp();
            }
        });

        penDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.penDown();
            }
        });
    }

    private void startMidSectionListeners(){
        drawTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.drawTT();
            }
        });
    }

    private synchronized void connect(BluetoothDevice tmp){
        Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG).show();
        DrawbotConnectThread connectDrawbot = new DrawbotConnectThread(tmp, handler);
        connectDrawbot.start();
        controller = new DrawbotCommand(connectDrawbot.getBTSocket());
    }
}
