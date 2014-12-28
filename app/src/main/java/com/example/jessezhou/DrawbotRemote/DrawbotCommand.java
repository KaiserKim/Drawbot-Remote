package com.example.jessezhou.DrawbotRemote;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Jesse Zhou on 12/27/2014.
 */
public class DrawbotCommand{

    OutputStream sender;

    public DrawbotCommand(BluetoothSocket btSocket){
        try {
            sender = btSocket.getOutputStream();
        }
        catch(IOException e){}
    }

    public void drawTT(){
        try {
            sender.write(Constants.DRAW_THETA_TAU);
            sender.flush();
        }
        catch(IOException e){}
    }

    public void stopDrawing(){
        try {
            sender.write(Constants.STOP_DRAWING);
            sender.flush();
        }
        catch(IOException e){}
    }

}
