package com.api;

public class ReceiveEndPointPara {

	// 小车编号
	private String carNumber;
	
	// 小车即时速度
	private String speed;
	
	// 小车行驶距离
	private String distance;
	
	// 小车霍尔感应器计数
	private int hoare;
	
	// 转向角度
	private String swerveAngle;
	
	// 发送时间
	private long time;
	
	// 变道成功
	private boolean changeSuccess = false;
	
	// 内外道：1 内道， 2 外道
	private String inOutSide;
	
	// 数据包解析正常
	private boolean isNormal = false;
	
	public String getCarNumber() {
	    return carNumber;
	}

	public void setCarNumber(String carNumber) {
	    this.carNumber = carNumber;
	}

	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public int getHoare() {
		return hoare;
	}

	public void setHoare(int hoare) {
		this.hoare = hoare;
	}

	public String getSwerveAngle() {
	    return swerveAngle;
	}

	public void setSwerveAngle(String swerveAngle) {
	    this.swerveAngle = swerveAngle;
	}

	public boolean isChangeSuccess() {
	    return changeSuccess;
	}

	public void setChangeSuccess(boolean changeSuccess) {
	    this.changeSuccess = changeSuccess;
	}

	public long getTime() {
	    return time;
	}

	public void setTime(long time) {
	    this.time = time;
	}

	public String getInOutSide() {
	    return inOutSide;
	}

	public void setInOutSide(String inOutSide) {
	    this.inOutSide = inOutSide;
	}
}
