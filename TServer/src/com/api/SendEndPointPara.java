package com.api;

public class SendEndPointPara {

	// С�����
       private String carNumber;
    
	// С��MAC��ַ
	private String MacAddress;
	
	// С����ʱ�ٶ�
	private String speed;

	// С��ת��0 ֱ�У�1 ��ת��2 ��ת
	private String swervePara;
	
	// С��ת��Ƕ�
	private String swerveAngle;
	
	// С����ȡ���ڻ���:1 ����1��2   ����2
	private String runningTrack;
	
	// ����������0��������ʻ�� 1������; 2:����
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
