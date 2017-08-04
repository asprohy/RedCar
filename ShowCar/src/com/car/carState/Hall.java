package com.car.carState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Hall {
    
    
    //这个表存储小车信息对应坐标，方向
    //程序运行时调用，从数据库中抓取出来
    public static HashMap<CarState, CarInfo> hallMap = new HashMap<CarState, CarInfo>();
    
    //初始化时需要调用，将数据库中信息取出来
    Hall(){
	getHallMap();
    }
    
    static ResultSet ret = null;
    
    public HashMap<CarState, CarInfo> getHallMap() {
	ArrayList<Object> parameter = new ArrayList<Object>();
    	parameter.add(ret);
 
    	String sql = "select road_id, hall_count, channel, x, y, direction from hallInfo";
    	DBHelper db1 = new DBHelper(sql);
    	ResultSet ret;
	try {
	    ret = db1.pst.executeQuery();
	    while(ret.next()){
		CarState carState = new CarState(ret.getInt(1), ret.getInt(2), ret.getString(3));
		CarInfo carInfo = new CarInfo(ret.getInt(4), ret.getInt(5), ret.getInt(6));
		
		hallMap.put(carState, carInfo);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    	db1.close();
	return hallMap;
    }
    //建表
    //将小车转角以及磁铁位置与车道（车队编号），Hall计数以及内外道绑定
    //Hall 计数对应的表信息应该有 1.霍尔计数坐标 2.小车行车方向
    
    //返回小车转角
    public static int getDirection(int group_id, int hall_count, String channel) {
	int direction = 0;
	CarState carState = new CarState(group_id, hall_count, channel);
	CarInfo cf = hallMap.get(carState);
	direction = cf.direction;
	return direction;
    }
    
    //返回小车坐标
    //获取的点
    public static Point getPoint(int group_id, int hall_count, String channel) {
	Point p = new Point();
	CarState carState = new CarState(group_id, hall_count, channel);
	CarInfo cf = hallMap.get(carState);
	p = cf.site;
	return p;
    }
}
