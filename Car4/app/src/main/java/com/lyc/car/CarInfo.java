package com.lyc.car;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/27.
 */

public class CarInfo {
    private int id;
    private int x;
    private int y;
    private int direction;
    private double speed;

    public CarInfo(){
        id = 0;
        x = 0;
        y = 0;
        direction = 0;
        speed = 0;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

   public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void showCarInfo(CarInfo carInfo){
        Log.d(TAG, "showCarInfo: "+ " carInfoId " + carInfo.getId() + " x: " + x + " y: " + y);
    }
}
