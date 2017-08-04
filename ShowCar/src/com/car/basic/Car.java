package com.car.basic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.car.carState.Hall;
import com.car.carState.Point;

public class Car {
    private Point centerOfCarPoint;
    private int car_id;
    private int group_id;
    
    private String channel;
    private int hall_count;
    private double distance;
    private int direction;
    
    private int headingDgree;
    private double speed;
    
    static String carImgString = "../resources/red_car_0.jpg";
    
    static File carImgFile = new File(carImgString);
    static BufferedImage carImg;
    
    public Car()
    {
	centerOfCarPoint = new Point();
	car_id = 0;
	group_id = 0;
	channel = "unknow";
	hall_count = -1;
	distance = 0;
	speed = 0;
	direction = 0;
    }
    
    //如果小车正在变道这个点应该还有变换
    public Car(int group_id, int car_id, int hall_count, int speed, double distance, int headingDgree, String channel)
    {
	this.car_id = car_id;
	this.group_id = group_id;
	this.hall_count = hall_count;
	this.speed = speed;
	this.distance = distance;
	this.headingDgree = headingDgree;
	this.channel = channel;
	this.direction = Hall.getDirection(group_id, hall_count, channel);
	this.centerOfCarPoint = Hall.getPoint(group_id, hall_count, channel);
    }
    
    //获取的小车图片应该和小车转角绑定
    public BufferedImage getCarImage() {

	carImgString = "../resources/red_car_" + String.valueOf(this.channel) + ".jpg";
	carImgFile = new File(carImgString);
	try {
	    carImg = ImageIO.read(carImgFile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return carImg;
    }
    
    public static Point getTheCarPoint() {
	Point p = new Point();
	//设计思路
	//1.假设知道小车上一次
	
	
	
	return p;
    }
    
    public Point getCenterOfCarPoint() {
	return centerOfCarPoint;
    }
    public void setCenterOfCarPoint(Point centerOfCarPoint) {
	this.centerOfCarPoint = centerOfCarPoint;
    }
    public int getCar_id() {
	return car_id;
    }
    public void setCar_id(int car_id) {
	this.car_id = car_id;
    }
    public int getGroup_id() {
	return group_id;
    }
    public void setGroup_id(int group_id) {
	this.group_id = group_id;
    }
    public String getChannel() {
	return channel;
    }
    public void setChannel(String channel) {
	this.channel = channel;
    }
    public int getHall_count() {
	return hall_count;
    }
    public void setHall_count(int hall_count) {
	this.hall_count = hall_count;
    }
    public double getDistance() {
	return distance;
    }
    public void setDistance(int diatance) {
	this.distance = diatance;
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

    public int getHeadingDgree() {
	return headingDgree;
    }

    public void setHeadingDgree(int headingDgree) {
	this.headingDgree = headingDgree;
    }
    
}
