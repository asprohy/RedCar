package com.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

import com.sql.JdbcUtilsImp;

public class CarFollowing{
	
	private boolean flag = false;
	private double distance ;
	private String macAdress;
	private String speed;	
	private String angle;
	private String direction;
	private String direct;
	

	public boolean startFollowing(int teamNumber, int carNumber){
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carN = new CarNumber();
		try {
			distance = imp.getSingleCarDistance(teamNumber, carNumber);
			//System.out.println("distance"+distance);
			
			if(distance < 0 || distance == 0){
				return false;
			}
			List<Map<String,Object>> list = imp.listCarIdForFollowing(teamNumber, distance);
			List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
			
			//System.out.println(direction);
			if(direction.equals("up"))
			{
				setDirect("0");//0 直行，1 左转，2 右转
			} else if(direction.equals("left")){
				setDirect("1");
			} else if(direction.equals("right")){
				setDirect("2");
			}
			
			String st= "";
			String sc= "";
			//System.out.println("list.size()"+list.size());
			if(list.isEmpty()){
				return false;
			}
			for(int i = 0; i<list.size(); i++) {
				SendEndPointPara output = new SendEndPointPara();
				macAdress = imp.getMacByCarId(list.get(i));
				st = String.valueOf(imp.getGroupNumByGroupId(imp.getGroupIdByMacAddress(macAdress)));
				sc = String.valueOf(imp.getCarNumByMacAddress(macAdress));
				output.setMacAddress(macAdress);
				output.setSpeed(speed);	
				output.setSwerveFlow("0");
				output.setSwervePara(getDirect());
				output.setSwerveAngle(angle);
				output.setCarNumber(carN.getCarNumber(st, sc));
				output.setRoadId(imp.getRoadIdByTeamAndCar(teamNumber,carNumber));
				
				outputlist.add(output);
			}
			IXbeeMessageConnect conn = new XbeeMessageConnect();
			conn.openComPort("COM6", 9600);
			conn.sendMessages(outputlist);
			conn.closeComPort();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("从历史数据表中单个小车行驶距离获取失败！");
			e.printStackTrace();
		}
		
		return flag;		
	}
	
	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}
	
	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getMacAdress() {
		return macAdress;
	}

	public void setMacAdress(String macAdress) {
		this.macAdress = macAdress;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}


}
