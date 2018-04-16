package com.map;

public class CarMapState {
    public int hallCount;
    public int road_id;
    public String channel;
    
    public CarMapState(int road_id, int hallCount, String channel) {
	// TODO Auto-generated constructor stub
	this.hallCount = hallCount;
	this.road_id = road_id;
	this.channel = channel;
    }

	public int getHallCount() {
		return hallCount;
	}

	public void setHallCount(int hallCount) {
		this.hallCount = hallCount;
	}

	public int getRoad_id() {
		return road_id;
	}

	public void setRoad_id(int road_id) {
		this.road_id = road_id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
    
    
}
