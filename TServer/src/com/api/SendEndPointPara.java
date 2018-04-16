package com.api;

public class SendEndPointPara {

	// 小车编号
       private String carNumber;
    
	// 小车MAC地址
	private String MacAddress;
	
	// 小车即时速度
	private String speed;

	// 小车转向：0 直行，1 左转，2 右转
	private String swervePara;
	
	// 小车转向角度
	private String swerveAngle;
	
	// 小车获取所在环道:1 环道1，2   环道2
	private String runningTrack;
	
	// 分流合流：0：正常行驶， 1：分流; 2:合流
	private String SwerveFlow;
	
	public String getSwerveFlow() {
	    return SwerveFlow;
	}

	public void setSwerveFlow(String swerveFlow) {
	    SwerveFlow = swerveFlow;
	}

	public String getMacAddress() {
	    return MacAddress;
	}

	public void setMacAddress(String macAddress) {
	    MacAddress = macAddress;
	}
	
	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getSwervePara() {
		return swervePara;
	}

	public void setSwervePara(String swervePara) {
		this.swervePara = swervePara;
	}

	public String getSwerveAngle() {
		return swerveAngle;
	}

	public void setSwerveAngle(String swerveAngle) {
		this.swerveAngle = swerveAngle;
	}

	public String getRunningTrack() {
	    return runningTrack;
	}

	public void setRunningTrack(String runningTrack) {
	    this.runningTrack = runningTrack;
	}
}
