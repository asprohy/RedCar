package com.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import Bean.Bean;

public class CarState implements Bean{


	/**
	 * 
	 */
	private static final long serialVersionUID = 77975516830049132L;
	private long id;
    private BigDecimal speed; 
	private BigDecimal drive_distance;
	private double speed1;
	private double drive_distance1;
	private long history_hall_count;
	private long car_id;
	private long group_id;
	private long road_id;
	private long heading_degree;
	private long send_time;
	private String channel;
	private boolean change_success;	
	private Timestamp create_time;
	private Timestamp update_time;
	
	public Object clone() {   
	        CarState o = null;   
	        try {   
	            o = (CarState) super.clone();   
	        } catch (CloneNotSupportedException e) {   
	            e.printStackTrace();   
	        }   
	        return o;   
	    }
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public double getSpeed() {
		return speed.doubleValue();
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	


	


	public long getHistory_hall_count() {
		return history_hall_count;
	}

	public void setHistory_hall_count(long history_hall_count) {
		this.history_hall_count = history_hall_count;
	}

	public long getCar_id() {
		return car_id;
	}

	public void setCar_id(long car_id) {
		this.car_id = car_id;
	}

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}

	public double getSpeed1() {
		return speed1;
	}

	public void setSpeed1(double speed1) {
		this.speed1 = speed1;
	}



	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public boolean isChange_success() {
		return change_success;
	}

	public void setChange_success(boolean change_success) {
		this.change_success = change_success;
	}

	public long getRoad_id() {
		return road_id;
	}

	public void setRoad_id(long road_id) {
		this.road_id = road_id;
	}

	public double getDrive_distance1() {
		return drive_distance1;
	}

	public void setDrive_distance1(double drive_distance1) {
		this.drive_distance1 = drive_distance1;
	}

	public long getHeading_degree() {
		return heading_degree;
	}

	public void setHeading_degree(long heading_degree) {
		this.heading_degree = heading_degree;
	}

	public double getDrive_distance() {
		return drive_distance.doubleValue();
	}

	public void setDrive_distance(BigDecimal drive_distance) {
		this.drive_distance = drive_distance;
	}
	
}

