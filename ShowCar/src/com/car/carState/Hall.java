package com.car.carState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Hall {
    
    
    //�����洢С����Ϣ��Ӧ���꣬����
    //��������ʱ���ã������ݿ���ץȡ����
    public static HashMap<CarState, CarInfo> hallMap = new HashMap<CarState, CarInfo>();
    
    //��ʼ��ʱ��Ҫ���ã������ݿ�����Ϣȡ����
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
    //����
    //��С��ת���Լ�����λ���복�������ӱ�ţ���Hall�����Լ��������
    //Hall ������Ӧ�ı���ϢӦ���� 1.������������ 2.С���г�����
    
    //����С��ת��
    public static int getDirection(int group_id, int hall_count, String channel) {
	int direction = 0;
	CarState carState = new CarState(group_id, hall_count, channel);
	CarInfo cf = hallMap.get(carState);
	direction = cf.direction;
	return direction;
    }
    
    //����С������
    //��ȡ�ĵ�
    public static Point getPoint(int group_id, int hall_count, String channel) {
	Point p = new Point();
	CarState carState = new CarState(group_id, hall_count, channel);
	CarInfo cf = hallMap.get(carState);
	p = cf.site;
	return p;
    }
}
