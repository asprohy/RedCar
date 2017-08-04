package com.car.entity;

public class MyCar {
	
	private int holzerCounting;
	private double distance;
	private int car_id;
	private String channel;
	
	public MyCar(int holzerCounting, String channel, int car_id, double distance) {
		// TODO Auto-generated constructor stub
		this.channel = channel;
		this.holzerCounting = holzerCounting;
		this.distance = distance;
		this.car_id = car_id;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getHolzerCounting() {
		return holzerCounting;
	}

	public void setHolzerCounting(int holzerCounting) {
		this.holzerCounting = holzerCounting;
	}

	
	public int getCar_id() {
		return car_id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	
	
}
