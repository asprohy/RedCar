package com.api;

public class ReceiveEndPointPara {

	// С�����
	private String carNumber;
	
	// С����ʱ�ٶ�
	private String speed;
	
	// С����ʻ����
	private String distance;
	
	// С��������Ӧ������
	private int hoare;
	
	// ת��Ƕ�
	private String swerveAngle;
	
	// ����ʱ��
	private long time;
	
	// ����ɹ�
	private boolean changeSuccess = false;
	
	// �������1 �ڵ��� 2 ���
	private String inOutSide;
	
	// ���ݰ���������
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
