package com.lyc.car;

/**
 * Created by Administrator on 2017/8/27.
 */

public class JamInfo {

    private int x;
    private int y;
    private int nextX;
    private int nextY;
    private int specialId;
    private int angle;

    public JamInfo(){
        x = 0;
        y = 0;
        nextX =0;
        nextY = 0;
        specialId = 0;
        angle = 0;
    }



    public JamInfo(int x, int y, int nextX, int nextY){
        this.x = x;
        this.y = y;
        this.nextX = nextX;
        this.nextY = nextY;
    }

    public JamInfo(int x, int y, int nextX, int nextY, int specialId, int angle){
        this.x = x;
        this.y = y;
        this.nextX = nextX;
        this.nextY = nextY;
        this.specialId = specialId;
        this.angle = angle;
    }


    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
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

    public int getNextX() {
        return nextX;
    }

    public void setNextX(int nextX) {
        this.nextX = nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void setNextY(int nextY) {
        this.nextY = nextY;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }
}
