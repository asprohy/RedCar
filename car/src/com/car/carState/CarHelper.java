package com.car.carState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class CarHelper {
    
    //获取所有小车坐标信息
    public static List<CarInfo> getCarsSiteInMap() {
	List<CarInfo> carInfoList = new LinkedList<CarInfo>();
	
	String sql = "select car_id from t_car_group";
	DBHelper db = new DBHelper(sql);
	ResultSet ret = null;
	
	try {
	    ret = db.pst.executeQuery();
	    while(ret.next()){
		int carId = ret.getInt(1);
		CarInfo carInfo = getCarSiteInMap(carId);
		carInfoList.add(carInfo);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    	db.close();
	
	return carInfoList;
    }
    
    //根据小车id获取小车坐标信息
    public static CarInfo getCarSiteInMap(int id) {

	//找到最近一条数据
    	String sql = "select car_id, group_id, history_hall_count, max(dirive_distance) as dirive_distance, max(send_time) as sen_time from car_history_data where car_id = 1 Group BY car_id, group_id, history_hall_count";
    	DBHelper db = new DBHelper(sql);
    	ResultSet ret;
    	int carId = 0;
    	int groupId = 0;
    	int hallCount = 0;
    	double driveDistance = 0;
    	double nearDistance = 0;
    	double detaDistance = 0;
    	int detaX = 0;
    	int detaY = 0;
    	String channel = "";
    	Point thisPoint = new Point();
    	Point nextPoint = new Point();

    	
    	int roadId = 0;
    	int direction = 0;
    	
    	//真实地图与图片地图比例
    	double rate = 1105 / 700;
    	
	try {
	    ret = db.pst.executeQuery();
	    carId = ret.getInt(1);
	    groupId = ret.getInt(2);
	    hallCount = ret.getInt(3);
	    driveDistance = ret.getDouble(4);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    	db.close();
    	roadId = groupIdToroadId(groupId);
    	
    	//取当前小车，当前霍尔值，distance最小的数据出来
    	String sql1 = "select min(dirive_distance) as dirive_distance from car_history_data  where history_hall_count = " + hallCount +" and car_id = " + carId;
    	DBHelper db1 = new DBHelper(sql1);
    	try {
	    ResultSet ret1 = db1.pst.executeQuery();
	    nearDistance = ret1.getDouble(1);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    	db1.close();
    	
    	//从状态表中获取内外道信息
    	String sql2 = "select channel from car_warning_info where car_id = " + carId;
    	DBHelper db2 = new DBHelper(sql2);
    	try {
    	    ResultSet ret2 = db2.pst.executeQuery();
    	    channel = ret2.getString(1);
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
    	db2.close();
    	
    	direction = Hall.getDirection(roadId, hallCount, channel);
    	thisPoint = Hall.getPoint(roadId, hallCount, channel);
 //   	nextPoint = Hall.getPoint(roadId, hallCount + 1, channel);
    	
    	detaDistance = driveDistance - nearDistance;
    	
    	boolean isSpecial = isSpecial(roadId, channel, hallCount, detaDistance);
    	boolean isChangeRoad = isChangeRoad();
    	
    	//不是特殊点就直接运算
    	if(!isSpecial)
    	{
    	    detaX = (int) (Math.cos(direction) * detaDistance);
	    detaY = (int) (Math.sin(direction) * detaDistance);
	
	    thisPoint.x = (int)((thisPoint.x + detaX) * rate);
	    thisPoint.y = (int)((thisPoint.y + detaY) * rate);
	    //是否在变道
    	    if(!isChangeRoad)
    	    {
        	CarInfo carInfo = new CarInfo(thisPoint.x, thisPoint.y, direction);
        	return carInfo;
    	    }else{
    		//变道的时候加一个增量
    		CarInfo carInfo = new CarInfo(thisPoint.x, thisPoint.y, direction);
        	return carInfo;
    	    }
    	    
    	}else{
    	    CarInfo carInfo = getSpecialCarInfo(roadId, channel, hallCount, detaDistance);
    	    return carInfo;
    	}    	
    }
    
    
    public static boolean isChangeRoad() {
	
	return false;
    }
    
    //判断这个霍尔点是不是转弯点
    public static boolean isSpecial(int roadId, String channel, int hall_count, double distance) {
	int ch;
	//内道1， 外道2
	if(channel.equals("inside")){
	    ch = 1;
	}
	else{
	    ch = 2;
	}
	
	String sql = "select * from car_bend_info where road_id = " + roadId + " and channel = " + ch + " and hall_count = " + hall_count;
    	DBHelper db = new DBHelper(sql);
    	ResultSet ret = null;
	try {
	    ret = db.pst.executeQuery();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	db.close();
	try {
	    if(ret.next())
	        return true;
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return false;
    }
    
    
    public static CarInfo getSpecialCarInfo(int roadId, String channel, int hall_count, double distance) {
	CarInfo carInfo = null;
	int ch;
	//内道1， 外道2
	if(channel.equals("inside")){
	    ch = 1;
	}
	else{
	    ch = 2;
	}
	int iDistance = (int) (distance / 10) * 10;
	String sql = "select x, y, direction from car_bend_info where road_id = " + roadId + " and channel = " + ch + " and hall_count = " + hall_count + "and distance = " + iDistance;
	DBHelper db = new DBHelper(sql);
    	ResultSet ret = null;
    	try {
	    ret = db.pst.executeQuery();
	    int x = ret.getInt(1);
	    int y = ret.getInt(1);
	    int direction = ret.getInt(1);
	    carInfo = new CarInfo(x, y, direction);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	db.close();
    	
	return carInfo;
    }
    
    
    
    
    // 用于存储转弯时候小车坐标信息
    // 道路id    内外道id    霍尔值num    distance    X    Y    direction
    static int special[][] = {{}};
    
    
    //将车队ID转化为道路ID
    public static int groupIdToroadId(int groupId){
	if(groupId == 1 || groupId == 2)
	    return 1;
	return 2;
    }

}
