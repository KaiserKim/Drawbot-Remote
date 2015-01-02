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

    public void forward(){
        try {
            sender.write(Constants.MOVE_FORWARD);
        }
        catch(IOException e){}
    }

    public void backward(){
        try {
            sender.write(Constants.MOVE_BACKWARD);
        }
        catch(IOException e){}
    }

    public void turnRight(){

        try {
            sender.write(Constants.MOVE_RIGHT);
        }
        catch(IOException e){}
    }

    public void turnLeft(){
        try {
            sender.write(Constants.MOVE_LEFT);
        }
        catch(IOException e){}
    }

    public void stop(){
        try {
            sender.write(Constants.MOVE_STOP);
        }
        catch(IOException e){}
    }

    public void penUp(){
        try {
            sender.write(Constants.PEN_UP);
        }
        catch(IOException e){}
    }

    public void penDown(){
        try {
            sender.write(Constants.PEN_DOWN);
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

}
